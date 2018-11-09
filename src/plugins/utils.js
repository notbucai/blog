
import moment from 'moment'//导入文件
import 'moment/locale/zh-cn';
export const formatTime = (time) => {
    return moment(time).fromNow();
}