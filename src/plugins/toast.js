import Vue from 'vue';

import maskInfo from '@/components/MaskInfo.vue'

function showToast(info, timeout = 1500) {

    const MaskInfo = Vue.extend(maskInfo);

    const toastDom = new MaskInfo({
        el:document.createElement('div')
    });
    toastDom.msg =info;

    // toastDom.$mount(document.createElement('div'));
    // vue实例必须要有挂载点否则不出时候，el就是$mount 只不过$mount可以延时挂载
    
    document.body.appendChild(toastDom.$el);

    setTimeout(() => {
        toastDom.show = false;
    }, timeout);

}

Vue.prototype.$toast = showToast