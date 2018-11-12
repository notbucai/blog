import Vue from 'vue';
import moment from 'moment'//导入文件
import 'moment/locale/zh-cn';

Vue.filter('timeFormat', function (time) {
    return moment(time).fromNow();
});
