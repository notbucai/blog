<template>
  <div class="blog">
    <ViewHeader />
    <!-- {{JSON.stringify($route)}} -->
    <main class="blog-content">
      <header class="blog-header">
        <h1 class="blog-title">Flink 原理与实现：如何处理反压问题</h1>
        <p class="blog-meta">
          <span>发布与 2018-08-23</span>
          <span>|</span>
          <span>阅读次数 4321</span>
        </p>
      </header>
      <main class="blog-main">
        <mavon-editor defaultOpen="preview" :toolbarsFlag="false" :subfield="false" :preview="false" v-model="text_html" />
      </main>
      <div class="comment">
        <h2 class="comment-title">评论留言</h2>
        <form action="" method="post">
          <textarea name="comment" class="comment-content" placeholder="这里输入留言内容"></textarea>
          <input type="text" name="uname" maxlength="8" autocomplete="off" class="comment-uname" placeholder="留言昵称">
          <button class="comment-submit">提交</button>
        </form>
        <section class="comment-list">
          <h2 class="comment-list-title">2条评论</h2>
          <article class="comment-list-item">
            <div class="comment-list-item-img">
              <img src="https://cdn.v2ex.com/gravatar/222?s=48&d=retro" alt="" srcset="">
            </div>
            <div class="comment-list-item-main">
              <div class="comment-list-item-main-info">
                <p class="comment-list-item-main-info-name">Name</p>
                <p class="comment-list-item-main-info-time">2018-09-23</p>
              </div>
              <div class="comment-list-item-main-content">
                <pre><span class="at">@name</span>input 的属性autocomplete 默认为on，其含义代表是否让浏览器自动记录之前输入的值。
很多时候，需要对客户的资料进行保密，防止浏览器软件或者恶意插件获取到，可以在input中加入autocomplete="off" 来关闭记录，系统需要保密的情况下可以使用此参数。</pre>
              </div>
              <div class="comment-list-item-main-reply">回复</div>
            </div>
          </article>
          <article class="comment-list-item">
            <div class="comment-list-item-img">
              <img src="https://cdn.v2ex.com/gravatar/222?s=48&d=retro" alt="" srcset="">
            </div>
            <div class="comment-list-item-main">
              <div class="comment-list-item-main-info">
                <p class="comment-list-item-main-info-name">Name</p>
                <p class="comment-list-item-main-info-time">2018-09-23</p>
              </div>
              <div class="comment-list-item-main-content">
                <pre><span class="at">@name</span>input 的属性autocomplete 默认为on，其含义代表是否让浏览器自动记录之前输入的值。
很多时候，需要对客户的资料进行保密，防止浏览器软件或者恶意插件获取到，可以在input中加入autocomplete="off" 来关闭记录，系统需要保密的情况下可以使用此参数。</pre>
              </div>
              <div class="comment-list-item-main-reply">回复</div>
            </div>
          </article>
        </section>
      </div>
    </main>
    <ViewFooter />
  </div>
</template>

<script>
import ViewHeader from "@/components/ViewHeader.vue";
import ViewFooter from "@/components/ViewFooter.vue";

export default {
  name: "blog",
  components: {
    ViewHeader,
    ViewFooter
  },
  created() {
    // console.log(this.$route.params.id);
  },
  data() {
    return {
      text_html: `<div class="post-body" itemprop="articleBody">
        <p>流处理系统需要能优雅地处理反压（backpressure）问题。反压通常产生于这样的场景：短时负载高峰导致系统接收数据的速率远高于它处理数据的速率。许多日常问题都会导致反压，例如，垃圾回收停顿可能会导致流入的数据快速堆积，或者遇到大促或秒杀活动导致流量陡增。反压如果不能得到正确的处理，可能会导致资源耗尽甚至系统崩溃。</p>
<p>目前主流的流处理系统 Storm/JStorm/Spark Streaming/Flink 都已经提供了反压机制，不过其实现各不相同。</p>
<p>Storm 是通过监控 Bolt 中的接收队列负载情况，如果超过高水位值就会将反压信息写到 Zookeeper ，Zookeeper 上的 watch 会通知该拓扑的所有 Worker 都进入反压状态，最后 Spout 停止发送 tuple。具体实现可以看这个 JIRA <a href="https://github.com/apache/storm/pull/700" target="_blank" rel="external">STORM-886</a>。</p>
<p>JStorm 认为直接停止 Spout 的发送太过暴力，存在大量问题。当下游出现阻塞时，上游停止发送，下游消除阻塞后，上游又开闸放水，过了一会儿，下游又阻塞，上游又限流，如此反复，整个数据流会一直处在一个颠簸状态。所以 JStorm 是通过逐级降速来进行反压的，效果会较 Storm 更为稳定，但算法也更复杂。另外 JStorm 没有引入 Zookeeper 而是通过 TopologyMaster 来协调拓扑进入反压状态，这降低了 Zookeeper 的负载。</p>
<a id="more"></a>
<h2 id="Flink-中的反压"><a href="#Flink-中的反压" class="headerlink" title="Flink 中的反压"></a>Flink 中的反压</h2><p>那么 Flink 是怎么处理反压的呢？答案非常简单：Flink 没有使用任何复杂的机制来解决反压问题，因为根本不需要那样的方案！它利用自身作为纯数据流引擎的优势来优雅地响应反压问题。下面我们会深入分析 Flink 是如何在 Task 之间传输数据的，以及数据流如何实现自然降速的。</p>
<p>Flink 在运行时主要由 <strong>operators</strong> 和 <strong>streams</strong> 两大组件构成。每个 operator 会消费中间态的流，并在流上进行转换，然后生成新的流。对于 Flink 的网络机制一种形象的类比是，Flink 使用了高效有界的分布式阻塞队列，就像 Java 通用的阻塞队列（BlockingQueue）一样。还记得经典的线程间通信案例：生产者消费者模型吗？使用 BlockingQueue 的话，一个较慢的接受者会降低发送者的发送速率，因为一旦队列满了（有界队列）发送者会被阻塞。Flink 解决反压的方案就是这种感觉。</p>
<p>在 Flink 中，这些分布式阻塞队列就是这些逻辑流，而队列容量是通过缓冲池（<code>LocalBufferPool</code>）来实现的。每个被生产和被消费的流都会被分配一个缓冲池。缓冲池管理着一组缓冲(<code>Buffer</code>)，缓冲在被消费后可以被回收循环利用。这很好理解：你从池子中拿走一个缓冲，填上数据，在数据消费完之后，又把缓冲还给池子，之后你可以再次使用它。</p>
<p>在解释 Flink 的反压原理之前，我们必须先对 Flink 中网络传输的内存管理有个了解。</p>
<h3 id="网络传输中的内存管理"><a href="#网络传输中的内存管理" class="headerlink" title="网络传输中的内存管理"></a>网络传输中的内存管理</h3><p>如下图所示展示了 Flink 在网络传输场景下的内存管理。网络上传输的数据会写到 Task 的 InputGate（IG） 中，经过 Task 的处理后，再由 Task 写到 ResultPartition（RS） 中。每个 Task 都包括了输入和输入，输入和输出的数据存在 <code>Buffer</code> 中（都是字节数据）。Buffer 是 MemorySegment 的包装类。</p>
<p><a href="http://img3.tbcdn.cn/5476e8b07b923/TB14fLsHVXXXXXWXFXXXXXXXXXX" class="fancybox" rel="group"><img src="http://img3.tbcdn.cn/5476e8b07b923/TB14fLsHVXXXXXWXFXXXXXXXXXX" alt=""></a></p>
<ol>
<li><p>TaskManager（TM）在启动时，会先初始化<code>NetworkEnvironment</code>对象，TM 中所有与网络相关的东西都由该类来管理（如 Netty 连接），其中就包括<code>NetworkBufferPool</code>。根据配置，Flink 会在 NetworkBufferPool 中生成一定数量（默认2048）的内存块 MemorySegment（关于 Flink 的内存管理，<a href="http://wuchong.me/blog/2016/04/29/flink-internals-memory-manage/">后续文章</a>会详细谈到），内存块的总数量就代表了网络传输中所有可用的内存。NetworkEnvironment 和 NetworkBufferPool 是 Task 之间共享的，每个 TM 只会实例化一个。</p>
</li>
<li><p>Task 线程启动时，会向 NetworkEnvironment 注册，NetworkEnvironment 会为 Task 的 InputGate（IG）和 ResultPartition（RP） 分别创建一个 LocalBufferPool（缓冲池）并设置可申请的 MemorySegment（内存块）数量。IG 对应的缓冲池初始的内存块数量与 IG 中 InputChannel 数量一致，RP 对应的缓冲池初始的内存块数量与 RP 中的 ResultSubpartition 数量一致。不过，每当创建或销毁缓冲池时，NetworkBufferPool 会计算剩余空闲的内存块数量，并平均分配给已创建的缓冲池。注意，这个过程只是指定了缓冲池所能使用的内存块数量，并没有真正分配内存块，只有当需要时才分配。为什么要动态地为缓冲池扩容呢？因为内存越多，意味着系统可以更轻松地应对瞬时压力（如GC），不会频繁地进入反压状态，所以我们要利用起那部分闲置的内存块。</p>
</li>
<li><p>在 Task 线程执行过程中，当 Netty 接收端收到数据时，为了将 Netty 中的数据拷贝到 Task 中，InputChannel（实际是 RemoteInputChannel）会向其对应的缓冲池申请内存块（上图中的①）。如果缓冲池中也没有可用的内存块且已申请的数量还没到池子上限，则会向 NetworkBufferPool 申请内存块（上图中的②）并交给 InputChannel 填上数据（上图中的③和④）。如果缓冲池已申请的数量达到上限了呢？或者 NetworkBufferPool 也没有可用内存块了呢？这时候，Task 的 Netty Channel 会暂停读取，上游的发送端会立即响应停止发送，拓扑会进入反压状态。当 Task 线程写数据到 ResultPartition 时，也会向缓冲池请求内存块，如果没有可用内存块时，会阻塞在请求内存块的地方，达到暂停写入的目的。</p>
</li>
<li><p>当一个内存块被消费完成之后（在输入端是指内存块中的字节被反序列化成对象了，在输出端是指内存块中的字节写入到 Netty Channel 了），会调用 <code>Buffer.recycle()</code> 方法，会将内存块还给 LocalBufferPool （上图中的⑤）。如果LocalBufferPool中当前申请的数量超过了池子容量（由于上文提到的动态容量，由于新注册的 Task 导致该池子容量变小），则LocalBufferPool会将该内存块回收给 NetworkBufferPool（上图中的⑥）。如果没超过池子容量，则会继续留在池子中，减少反复申请的开销。</p>
</li>
</ol>
<h3 id="反压的过程"><a href="#反压的过程" class="headerlink" title="反压的过程"></a>反压的过程</h3><p>下面这张图简单展示了两个 Task 之间的数据传输以及 Flink 如何感知到反压的：</p>
<p><a href="http://img3.tbcdn.cn/5476e8b07b923/TB1rCIvJpXXXXcKXXXXXXXXXXXX" class="fancybox" rel="group"><img src="http://img3.tbcdn.cn/5476e8b07b923/TB1rCIvJpXXXXcKXXXXXXXXXXXX" alt=""></a></p>
<ol>
<li>记录“A”进入了 Flink 并且被 Task 1 处理。（这里省略了 Netty 接收、反序列化等过程）</li>
<li>记录被序列化到 buffer 中。</li>
<li>该 buffer 被发送到 Task 2，然后 Task 2 从这个 buffer 中读出记录。</li>
</ol>
<p><strong>不要忘了：记录能被 Flink 处理的前提是，必须有空闲可用的 Buffer。</strong></p>
<p>结合上面两张图看：Task 1 在输出端有一个相关联的 LocalBufferPool（称缓冲池1），Task 2 在输入端也有一个相关联的 LocalBufferPool（称缓冲池2）。如果缓冲池1中有空闲可用的 buffer 来序列化记录 “A”，我们就序列化并发送该 buffer。</p>
<p>这里我们需要注意两个场景：</p>
<ul>
<li>本地传输：如果 Task 1 和 Task 2 运行在同一个 worker 节点（TaskManager），该 buffer 可以直接交给下一个 Task。一旦 Task 2 消费了该 buffer，则该 buffer 会被缓冲池1回收。如果 Task 2 的速度比 1 慢，那么 buffer 回收的速度就会赶不上 Task 1 取 buffer 的速度，导致缓冲池1无可用的 buffer，Task 1 等待在可用的 buffer 上。最终形成 Task 1 的降速。</li>
<li>远程传输：如果 Task 1 和 Task 2 运行在不同的 worker 节点上，那么 buffer 会在发送到网络（TCP Channel）后被回收。在接收端，会从 LocalBufferPool 中申请 buffer，然后拷贝网络中的数据到 buffer 中。如果没有可用的 buffer，会停止从 TCP 连接中读取数据。在输出端，通过 Netty 的水位值机制来保证不往网络中写入太多数据（后面会说）。如果网络中的数据（Netty输出缓冲中的字节数）超过了高水位值，我们会等到其降到低水位值以下才继续写入数据。这保证了网络中不会有太多的数据。如果接收端停止消费网络中的数据（由于接收端缓冲池没有可用 buffer），网络中的缓冲数据就会堆积，那么发送端也会暂停发送。另外，这会使得发送端的缓冲池得不到回收，writer 阻塞在向 LocalBufferPool  请求 buffer，阻塞了 writer 往 ResultSubPartition 写数据。</li>
</ul>
<p>这种固定大小缓冲池就像阻塞队列一样，保证了 Flink 有一套健壮的反压机制，使得 Task 生产数据的速度不会快于消费的速度。我们上面描述的这个方案可以从两个 Task 之间的数据传输自然地扩展到更复杂的 pipeline 中，保证反压机制可以扩散到整个 pipeline。</p>
<h3 id="Netty-水位值机制"><a href="#Netty-水位值机制" class="headerlink" title="Netty 水位值机制"></a>Netty 水位值机制</h3><p>下方的代码是初始化 NettyServer 时配置的水位值参数。</p>
<figure class="highlight java"><table><tbody><tr><td class="code"><pre><div class="line"><span class="comment">// 默认高水位值为2个buffer大小, 当接收端消费速度跟不上，发送端会立即感知到</span></div><div class="line">bootstrap.childOption(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, config.getMemorySegmentSize() + <span class="number">1</span>);</div><div class="line">bootstrap.childOption(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, <span class="number">2</span> * config.getMemorySegmentSize());</div></pre></td></tr></tbody></table></figure>
<p>当输出缓冲中的字节数超过了高水位值, 则 Channel.isWritable() 会返回false。当输出缓存中的字节数又掉到了低水位值以下, 则 Channel.isWritable() 会重新返回true。Flink 中发送数据的核心代码在 <code>PartitionRequestQueue</code> 中，该类是 server channel pipeline 的最后一层。发送数据关键代码如下所示。</p>
<figure class="highlight java"><table><tbody><tr><td class="code"><pre><div class="line"><span class="function"><span class="keyword">private</span> <span class="keyword">void</span> <span class="title">writeAndFlushNextMessageIfPossible</span><span class="params">(<span class="keyword">final</span> Channel channel)</span> <span class="keyword">throws</span> IOException </span>{</div><div class="line">  <span class="keyword">if</span> (fatalError) {</div><div class="line">    <span class="keyword">return</span>;</div><div class="line">  }</div><div class="line"></div><div class="line">  Buffer buffer = <span class="keyword">null</span>;</div><div class="line"></div><div class="line">  <span class="keyword">try</span> {</div><div class="line">    <span class="comment">// channel.isWritable() 配合 WRITE_BUFFER_LOW_WATER_MARK </span></div><div class="line">    <span class="comment">// 和 WRITE_BUFFER_HIGH_WATER_MARK 实现发送端的流量控制</span></div><div class="line">    <span class="keyword">if</span> (channel.isWritable()) {</div><div class="line">      <span class="comment">// 注意: 一个while循环也就最多只发送一个BufferResponse, 连续发送BufferResponse是通过writeListener回调实现的</span></div><div class="line">      <span class="keyword">while</span> (<span class="keyword">true</span>) {</div><div class="line">        <span class="keyword">if</span> (currentPartitionQueue == <span class="keyword">null</span> &amp;&amp; (currentPartitionQueue = queue.poll()) == <span class="keyword">null</span>) {</div><div class="line">          <span class="keyword">return</span>;</div><div class="line">        }</div><div class="line"></div><div class="line">        buffer = currentPartitionQueue.getNextBuffer();</div><div class="line"></div><div class="line">        <span class="keyword">if</span> (buffer == <span class="keyword">null</span>) {</div><div class="line">          <span class="comment">// 跳过这部分代码</span></div><div class="line">          ...</div><div class="line">        }</div><div class="line">        <span class="keyword">else</span> {</div><div class="line">          <span class="comment">// 构造一个response返回给客户端</span></div><div class="line">          BufferResponse resp = <span class="keyword">new</span> BufferResponse(buffer, currentPartitionQueue.getSequenceNumber(), currentPartitionQueue.getReceiverId());</div><div class="line"></div><div class="line">          <span class="keyword">if</span> (!buffer.isBuffer() &amp;&amp;</div><div class="line">              EventSerializer.fromBuffer(buffer, getClass().getClassLoader()).getClass() == EndOfPartitionEvent.class) {</div><div class="line">            <span class="comment">// 跳过这部分代码。batch 模式中 subpartition 的数据准备就绪，通知下游消费者。</span></div><div class="line">            ...</div><div class="line">          }</div><div class="line"></div><div class="line">          <span class="comment">// 将该response发到netty channel, 当写成功后, </span></div><div class="line">          <span class="comment">// 通过注册的writeListener又会回调进来, 从而不断地消费 queue 中的请求</span></div><div class="line">          channel.writeAndFlush(resp).addListener(writeListener);</div><div class="line"></div><div class="line">          <span class="keyword">return</span>;</div><div class="line">        }</div><div class="line">      }</div><div class="line">    }</div><div class="line">  }</div><div class="line">  <span class="keyword">catch</span> (Throwable t) {</div><div class="line">    <span class="keyword">if</span> (buffer != <span class="keyword">null</span>) {</div><div class="line">      buffer.recycle();</div><div class="line">    }</div><div class="line"></div><div class="line">    <span class="keyword">throw</span> <span class="keyword">new</span> IOException(t.getMessage(), t);</div><div class="line">  }</div><div class="line">}</div><div class="line"></div><div class="line"><span class="comment">// 当水位值降下来后（channel 再次可写），会重新触发发送函数</span></div><div class="line"><span class="meta">@Override</span></div><div class="line"><span class="function"><span class="keyword">public</span> <span class="keyword">void</span> <span class="title">channelWritabilityChanged</span><span class="params">(ChannelHandlerContext ctx)</span> <span class="keyword">throws</span> Exception </span>{</div><div class="line">  writeAndFlushNextMessageIfPossible(ctx.channel());</div><div class="line">}</div></pre></td></tr></tbody></table></figure>
<p>核心发送方法中如果channel不可写，则会跳过发送。当channel再次可写后，Netty 会调用该Handle的 <code>channelWritabilityChanged</code> 方法，从而重新触发发送函数。</p>
<h3 id="反压实验"><a href="#反压实验" class="headerlink" title="反压实验"></a>反压实验</h3><p>另外，<a href="http://data-artisans.com/how-flink-handles-backpressure/" target="_blank" rel="external">官方博客</a>中为了展示反压的效果，给出了一个简单的实验。下面这张图显示了：随着时间的改变，生产者（黄色线）和消费者（绿色线）每5秒的平均吞吐与最大吞吐（在单一JVM中每秒达到8百万条记录）的百分比。我们通过衡量task每5秒钟处理的记录数来衡量平均吞吐。该实验运行在单 JVM 中，不过使用了完整的 Flink 功能栈。</p>
<p><a href="http://img3.tbcdn.cn/5476e8b07b923/TB1geTaHVXXXXcXXVXXXXXXXXXX" class="fancybox" rel="group"><img src="http://img3.tbcdn.cn/5476e8b07b923/TB1geTaHVXXXXcXXVXXXXXXXXXX" alt=""></a></p>
<p>首先，我们运行生产task到它最大生产速度的60%（我们通过Thread.sleep()来模拟降速）。消费者以同样的速度处理数据。然后，我们将消费task的速度降至其最高速度的30%。你就会看到背压问题产生了，正如我们所见，生产者的速度也自然降至其最高速度的30%。接着，停止消费task的人为降速，之后生产者和消费者task都达到了其最大的吞吐。接下来，我们再次将消费者的速度降至30%，pipeline给出了立即响应：生产者的速度也被自动降至30%。最后，我们再次停止限速，两个task也再次恢复100%的速度。总而言之，我们可以看到：生产者和消费者在 pipeline 中的处理都在跟随彼此的吞吐而进行适当的调整，这就是我们希望看到的反压的效果。</p>
<h2 id="反压监控"><a href="#反压监控" class="headerlink" title="反压监控"></a>反压监控</h2><p>在 Storm/JStorm 中，只要监控到队列满了，就可以记录下拓扑进入反压了。但是 Flink 的反压太过于天然了，导致我们无法简单地通过监控队列来监控反压状态。Flink 在这里使用了一个 trick 来实现对反压的监控。如果一个 Task 因为反压而降速了，那么它会卡在向 <code>LocalBufferPool</code> 申请内存块上。那么这时候，该 Task 的 stack trace 就会长下面这样：</p>
<figure class="highlight stylus"><table><tbody><tr><td class="code"><pre><div class="line">java<span class="selector-class">.lang</span><span class="selector-class">.Object</span><span class="selector-class">.wait</span>(Native Method)</div><div class="line">o<span class="selector-class">.a</span><span class="selector-class">.f</span>.[...]<span class="selector-class">.LocalBufferPool</span><span class="selector-class">.requestBuffer</span>(LocalBufferPool<span class="selector-class">.java</span>:<span class="number">163</span>)</div><div class="line">o<span class="selector-class">.a</span><span class="selector-class">.f</span>.[...]<span class="selector-class">.LocalBufferPool</span><span class="selector-class">.requestBufferBlocking</span>(LocalBufferPool<span class="selector-class">.java</span>:<span class="number">133</span>) &lt;--- BLOCKING request</div><div class="line">[...]</div></pre></td></tr></tbody></table></figure>
<p>那么事情就简单了。通过不断地采样每个 task 的 stack trace 就可以实现反压监控。</p>
<p><a href="http://img3.tbcdn.cn/5476e8b07b923/TB1T3cJJpXXXXXLXpXXXXXXXXXX" class="fancybox" rel="group"><img src="http://img3.tbcdn.cn/5476e8b07b923/TB1T3cJJpXXXXXLXpXXXXXXXXXX" alt=""></a></p>
<p>Flink 的实现中，只有当 Web 页面切换到某个 Job 的 Backpressure 页面，才会对这个 Job 触发反压检测，因为反压检测还是挺昂贵的。JobManager 会通过 Akka 给每个 TaskManager 发送<code>TriggerStackTraceSample</code>消息。默认情况下，TaskManager 会触发100次 stack trace 采样，每次间隔 50ms（也就是说一次反压检测至少要等待5秒钟）。并将这 100 次采样的结果返回给 JobManager，由 JobManager 来计算反压比率（反压出现的次数/采样的次数），最终展现在 UI 上。UI 刷新的默认周期是一分钟，目的是不对 TaskManager 造成太大的负担。</p>
<h2 id="总结"><a href="#总结" class="headerlink" title="总结"></a>总结</h2><p>Flink 不需要一种特殊的机制来处理反压，因为 Flink 中的数据传输相当于已经提供了应对反压的机制。因此，Flink 所能获得的最大吞吐量由其 pipeline 中最慢的组件决定。相对于 Storm/JStorm 的实现，Flink 的实现更为简洁优雅，源码中也看不见与反压相关的代码，无需 Zookeeper/TopologyMaster 的参与也降低了系统的负载，也利于对反压更迅速的响应。</p>

      
    </div>`
    };
  }
};
</script>

<style lang="scss" scoped>
.blog {
  &-content {
    width: 900px;
    margin: 60px auto 30px;
  }
  &-header {
    text-align: center;
  }
  &-title {
    font-size: 26px;
    font-weight: normal;
  }
  &-meta {
    font-size: 12px;
    margin: 20px 0;
    span {
      margin-right: 10px;
      color: #888;
    }
  }
}
.comment {
  margin-top: 20px;
  background-color: #fdfdfd;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  box-shadow: 0 0px 3px rgba(0, 0, 0, 0.157), 0 0px 3px rgba(0, 0, 0, 0.227);
  &-content {
    width: 100%;
    height: 200px;
    border: 2px solid #f1f1f1;
    background-color: #fff;
    border-radius: 4px;
    font-size: 14px;
    color: #444;
    font-weight: 400;
    padding: 10px;
    box-sizing: border-box;
    resize: none;
    &:focus {
      outline: none;
    }
  }
  &-uname {
    width: 200px;
    padding: 10px 16px;
    margin-top: 20px;
    font-size: 16px;
    border: 2px solid #f1f1f1;
    background-color: #fff;
    color: #666;
    &:focus {
      outline: none;
    }
  }
  &-title {
    margin-bottom: 20px;
  }
  &-submit {
    margin-left: 20px;
    padding: 10px 16px;
    border: none;
    background-color: #000;
    color: #fff;
    cursor: pointer;
  }

  &-list {
    margin: 20px 0;

    &-title {
      margin-top: 40px;
      margin-bottom: 20px;
      border-left: 6px solid #000;
      padding-left: 16px;
    }
    &-item {
      margin-bottom: 20px;
      background-color: #fff;
      box-shadow: 2px 2px 8px #dddd;
      box-sizing: border-box;
      // border-radius: 6px;
      width: 100%;
      position: relative;
      padding: 20px;
      &-img {
        position: absolute;
        left: 20px;
        top: 20px;
        width: 48px;
        height: 48px;
        > img {
          border-radius: 6px;
          width: 100%;
          height: 100%;
        }
      }
      &-main {
        margin-left: 70px;
        &-info {
          display: flex;
          align-items: flex-end;
          &-name {
            font-size: 16px;
            color: #35aadc;
          }
          &-time {
            font-size: 12px;
            color: #888;
            margin-left: 10px;
          }
        }
        &-content {
          margin-top: 6px;
          font-size: 14px;
          line-height: 24px;
          overflow: hidden;
          pre {
            color: #444;
            white-space: pre-wrap;
            > .at {
              font-size: 16px;
              color: #35aadc;
              margin-right: 10px;
            }
          }
        }
        &-reply {
          position: absolute;
          bottom: 0;
          right: 0;
          padding: 6px 12px;
          font-size: 14px;
          color: #999;
          cursor: pointer;
          background-color: #f1f1f1;
          &:hover {
            color: #fff;
            background-color: #35aadc;
          }
        }
      }
    }
  }
}
</style>

