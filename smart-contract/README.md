## Web3j

- 지갑 생성

  ```bash
  <dependency>
  	<groupId>org.web3j</groupId>
  	<artifactId>core</artifactId>
  	<version>4.2.0</version>
  </dependency>
  ```

  ```bash
  String seed = UUID.randomUUID().toString();
  
  try {
  	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
  	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
  
  	String privKey = privateKeyInDec.toString(16);
  
  	WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
  	String address = "0x" + aWallet.getAddress();
  	
  	System.out.println("address: " + address);
  	System.out.println("private key: " + privKey);
  } catch (Exception e) {
  	e.printStackTrace();
  }
  ```

## Web3js

- 지갑 생성

  ```jsx
  var web3 = new Web3(new Web3.providers.HttpProvider('<http://127.0.0.1:8545/>'));
  var newAccount = await web3.eth.accounts.create();
  ```

- 함수 호출

  ```jsx
  //1. call
  //아래 함수와 같이 contract 개체의 .methods.{method name}.call()로 호출할 수 있다.
  export function getPrice(itemId, onConfirm, onFail) {
    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
    var contract = createFactoryContract(web3);
    contract.methods
      .items(itemId) //contract method name
      .call()
      .then(res => {
        onConfirm(res.price);
      })
      .catch(err => {
        onFail(err);
      });
  }
  
  //2. send
  //nonce의 역할: 트랜잭션 replay 방지, 외부 유저가 위변조하지 않기 위함(메타마스크는 자체적으로 지원)
  let nonce = await this.web3.eth.getTransactionCount(this.account);
  //transaction을 발생시키고 싶은 경우 ex.setBloodCard, registerService
  this.lotteryContract.methods.betAndDistribution().send({from, value, nonce: nonce});
  
  
  ```

## Dapp 데이터 관리

- **Read**

1. 스마트 컨트랙트를 직접 call, batch read call
2. event log catch (http polling, websocket)
3. init과 동시에 past event를 가져온다.
4. ws으로 geth 또는 infura와 연결한다.
5. ws으로 원하는 이벤트를 subscribe한다 > ws를 사용할 수 없다면 롱 폴링을 이용한다.

- **Event**

  ```
  //3. filter - event log 가져오기
  //event란 주로 블록체인 외부에서 로그를 catch하기 위해 사용함
  //web3.eth.subscribe를 사용하면 웹소켓을 사용하여 이벤트를 지속적으로 받을 수 있도록 할 수 있다.
  async getBetEvent(){
  	const records = [];
  	let events = await this.lotteryContract.getPastEvent('BET',{fromBlock:0, toBlock:'latest'});
  }
  
  //event return값 파싱해서 저장하는 ver
  getBetEvents = async () => {
  	const records = [];
  	let events = await this.lotteryContract.getPastEvents('BET', {fromBlock:0, toBlock:'latest'});
  	for(let i = 0; i < events.length ; i++){
  		const record = {};
  		record.index = parseInt(events[i].returnValues.index, 10).toString();
  		record.better = events[i].returnValues.better;
  		record.betBlockNumber = events[i].blockNumber;
  		record.targetBlockNumber = events[i].returnValues.answerBlockNumber.toString();
  		record.challenges = events[i].returnValues.challenges;
  		record.win = "Not Revealed";
  		record.answer = "0x00";
  		records.unshift(record); //react 문법
  	}
  }
  ```

  - 반복해서 데이터를 가지고 오기

  ```jsx
  await this.initWeb3;
  setInterval(this.pollData, 1000); //1000ms(1초) 간격으로 contract로 부터 data를 가져옴
  
  pollData = async () => {
  	await this.getPot(); //should return private var potfrom contract
  }
  ```



## Reference

- 이벤트 관련 readdoc

  https://web3js.readthedocs.io/en/v1.5.2/web3-eth-contract.html#events

- 프라이빗 네트워크 구축 및 스마트 컨트랙트 실습

  https://y8ncastle.world/2020/02/25/education/ethereum/ethereum-private-network/