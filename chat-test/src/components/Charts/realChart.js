import React, { useEffect, useState } from 'react';
import ReactApexChart from 'react-apexcharts';
import ApexCharts from 'apexcharts';

function ApexChart(){
  var lastDate = 0;
  var data = []
  var TICKINTERVAL = 86400000
  let XAXISRANGE = 100000000
  const [options, setOptions] = useState({
    chart: {
      id: 'realtime',
      height: 350,
      type: 'line',
      animations: {
        enabled: true,
        easing: 'linear',
        dynamicAnimation: {
          speed: 1000
        }
      },
      toolbar: {
        show: false
      },
      zoom: {
        enabled: false
      }
    },
    dataLabels: {
      enabled: false
    },
    stroke: {
      curve: 'smooth'
    },
    title: {
      text: 'Dynamic Updating Chart',
      align: 'left'
    },
    markers: {
      size: 0
    },
    xaxis: {
      type: 'datetime',
      range: XAXISRANGE
    },
    yaxis: {
      max: 100
    },
    legend: {
      show: false
    },
  });

  function getNewSeries(baseval, yrange) {
    var newDate = baseval + TICKINTERVAL;
    lastDate = newDate
  
    for(var i = 0; i< data.length - 10; i++) {
      // IMPORTANT
      // we reset the x and y of the data which is out of drawing area
      // to prevent memory leaks
      data[i].x = newDate - XAXISRANGE - TICKINTERVAL
      data[i].y = 0
    }
  
    data.push({
      x: newDate,
      y: Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min
    })
    console.log(data)
  }

  const [series, setSeries] = useState([
    {
      data: data.slice()
    }
  ]);

  useEffect(() => {
    const interval = setInterval(() => {
      getNewSeries(lastDate, {
        min: 10,
        max: 90
      });

      
    ApexCharts.exec('realtime', 'updateSeries', [
        {
          data: data
        }
      ]);
    }, 1000);

    return () => {
      clearInterval(interval);
    };
  }, []);

  return (
    <div id="chart">
      <ReactApexChart options={options} series={series} type="line" height={350} />
    </div>
  );
};

export default ApexChart;
