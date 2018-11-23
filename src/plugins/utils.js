
import moment from 'moment'//导入文件
import 'moment/locale/zh-cn';
export const formatTime = (time) => {
    return moment(time).fromNow();
}


export const formatterTime = (time) => {
    const date = new Date(time);
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}-${String(date.getDate()).padStart(2, "0")} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}:${String(date.getSeconds()).padStart(2, "0")}`;
}
