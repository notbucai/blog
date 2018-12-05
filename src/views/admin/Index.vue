<template>
  <div class="admin-index">
    <div ref="myChart" :style="{width: '100%', height: '500px'}"></div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

import { WebSocketUrl } from "@/config/config.js";

// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");
require("echarts/lib/chart/line");
require("echarts/lib/component/timeline");
// 引入提示框和标题组件
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");

export default {
  name: "AdminIndex",

  components: {},
  created() {
    const adminSocket = new WebSocket(
      WebSocketUrl + "BucaiBlog/websocket/admin"
    );
    adminSocket.addEventListener("open", () => {
      adminSocket.addEventListener("message", ({ returnValue, data }) => {
        if (returnValue) {
          // console.log(JSON.parse(data));
          this.drawLine(JSON.parse(data));
        }
      });
    });
  },
  mounted() {
    // this.drawLine({});
    this.init();
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(["dataLine"]),
    option() {
      return {
        xAxis: {
          type: "category",
          boundaryGap: false,
          axisLabel: {
            formatter: function(value) {
              let date = new Date(Number(value));
              if (value === 0) {
                date = new Date();
              }
              return [
                date.getHours(),
                date
                  .getMinutes()
                  .toString()
                  .padStart(2, "0"),
                date
                  .getSeconds()
                  .toString()
                  .padStart(2, "0")
              ].join(":");
            }
          },
          data: this.dataLine.map(val => {
            //   console.log(val);
            return val.date;
          })
        },
        tooltip: {
          trigger: "axis",
          formatter(a) {
            let _o = "";
            // seriesName
            const date = new Date(Number(a[0].name));
            const date_v = [
              date.getHours(),
              date
                .getMinutes()
                .toString()
                .padStart(2, "0"),
              date
                .getSeconds()
                .toString()
                .padStart(2, "0")
            ].join(":");
            _o += `${date_v}<br/>`;
            for (let i = 0; i < a.length; i++) {
              _o += `${a[i].seriesName} ${a[i].value}%<br/>`;
            }
            return _o;
          }
        },

        yAxis: {
          type: "value",
          max: 100,
          min: 0,
          axisLabel: {
            formatter: function(val) {
              return val + "%";
            }
          }
        },
        series: [
          {
            name: "CPU",
            data: this.dataLine.map(val => {
              return val.value.cpu;
            }),
            type: "line",
            areaStyle: {
              normal: {
                opacity: 0
              }
            }
          },

          {
            name: "内存",
            data: this.dataLine.map(val => {
              return val.value.ram;
            }),
            type: "line",
            areaStyle: {
              normal: {
                opacity: 0
              }
            }
          }
        ]
      };
    }
  },
  methods: {
    ...mapActions(["getDataLine", "initDataLine"]),
    rangArr(len) {
      return new Array(len).fill({
        date: Date.now(),
        value: {
          cpu: 0,
          ram: 0
        }
      });
    },
    init() {
      const myChart = (this.myChart = echarts.init(this.$refs["myChart"]));
      // 绘制图表
      this.initDataLine(this.rangArr(30));
      //   console.log(this.dataLine);
      myChart.setOption(this.option);
    },
    drawLine(data) {
      // 基于准备好的dom，初始化echarts实例
      //   console.log(this.$refs.myChart);
      // console.log(this.dataLine);

      this.getDataLine(data);

      this.myChart.setOption(this.option);
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.admin-index {
  background: #f6f6f6;
  height: auto;
  box-sizing: border-box;
}
</style>
