import './App.css';
import { useState } from 'react';
import React from 'react';
import LogCheck from './logCheck';
import ApexChart from './components/Charts/realChart.js'
function App() {
  return (
    <div>
      <div>
      <LogCheck></LogCheck>
      <ApexChart></ApexChart>
      </div>
      
    </div>
  );
}

export default App;

