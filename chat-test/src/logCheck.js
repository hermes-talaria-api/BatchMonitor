import './App.css';
import { useState , useRef, useEffect} from 'react';
import * as StompJs from '@stomp/stompjs';

function LogCheck(){

/*stomp 관련 */
  const client = useRef({}); 
  const [log,setLog] = useState("");
  const connect = () =>{
      client.current = new StompJs.Client({
          brokerURL: 'ws://localhost:8080/ws/monitoring',
          onConnect:() =>{
             // Do something, all subscribes must be done is this callback
            console.log("연결 SUB");
              subscribe();
          },
      });
      client.current.activate();
  }
  useEffect(() => {
    connect(); // 마운트시 실행
    return () => disconnect(); // 언마운트 시 실행
  },[]);


  const disconnect = () => {
      client.current.deactivate(); // 활성화된 연결 끊기 
  };

  const subscribe = () => {
      client.current.subscribe('/sub/log', (res) => { // server에게 메세지 받으면
        // console.log(res)
        const json_body = JSON.parse(res.body);
        console.log(json_body);
        // setLog(json_body);
        // console.log(res.body);
      });
    };

    // const publish = () =>{
    //   client.current.publish({
    //     destination: '/pub/normal/' + applyId,
    //     body:JSON.stringify( {id:applyId, price:myPrice.current , memberId:1, itemId:1}),
    //     skipContentLengthHeader: true,
    //   });
      
    // }



  return (
    <div className="App">
      <p>log : {log}</p>
      <p>---------------------------------</p>
    </div>
  );

}

export default LogCheck;
