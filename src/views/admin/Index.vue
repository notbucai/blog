<template>
    <div class="admin-index">
        <div ref="myChart" :style="{width: '100%', height: '500px'}"></div>
    </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");
// 引入柱状图
require("echarts/lib/chart/bar");
require("echarts/lib/chart/line");
require("echarts/lib/component/timeline");
// 引入提示框和标题组件
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");

export default {
  name: "AdminIndex",

  components: {},
  mounted() {
    this.drawLine();
  },
  data() {
    return {
      dataLine: [
        {
          date: 1542351311142,
          value: {
            cpu: 0,
            ram: 0
          }
        }
      ],
      dataLineTime: []
    };
  },
  computed: {
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
                date.getMinutes(),
                date.getSeconds()
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
              //   console.log(val);
              return val.value.cpu;
            }),
            type: "line",
            areaStyle: {
              normal: {
                opacity: 0
              }
            },
            lineStyle: {
              normal: {
                color: "#f44336"
                // width: 1
              }
            }
          },

          {
            data: this.dataLine.map(val => {
              //   console.log(val);
              return val.value.ram;
            }),
            type: "line",
            areaStyle: {
              normal: {
                opacity: 0
              }
            },
            name:'内存',
            lineStyle: {
              normal: {
                color: "#f4f336"
                // width: 1
              }
            },
            markLine: {
              silent: true
            }
          }
        ]
      };
    }
  },
  methods: {
    rangArr(len) {
      let arr = [];
      for (let i = 0; i < len; i++) {
        arr[i] = {
          date: Date.now(),
          value: {
            cpu: 0,
            ram: 0
          }
        };
      }
      return arr;
    },
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
    //   console.log(this.$refs.myChart);

      var myChart = echarts.init(this.$refs["myChart"]);
      // 绘制图表
      this.dataLine = this.rangArr(30);
    //   console.log(this.dataLine);

      for (let i = 0; i < 60; i++) {
        this.dataLineTime[i] = i + 1;
      }
      myChart.setOption(this.option);
      setInterval(() => {
        this.dataLine = [
          ...this.dataLine.filter((v, i) => {
            return i;
          }),
          {
            date: Date.now(),
            value: {
              cpu: (Math.random() * 100) | 0,
              ram: (Math.random() * 100) | 0
            }
          }
        ];

        myChart.setOption(this.option);
      }, 1000);
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
</style>
