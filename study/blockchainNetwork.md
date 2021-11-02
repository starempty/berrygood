### 최종 geth 명령어

geth --networkid 921 --maxpeers 2 --datadir dev --rpcaddr "0.0.0.0" --rpc --rpcport 8545 --port 9991 --unlock 0 --password pw --rpccorsdomain "*" --rpcapi="db,eth,net,web3,personal,admin" --mine --miner.threads 1 console 2>> dev/geth.log

geth --networkid 921 --maxpeers 2 --syncmode 'fast' --datadir dev --rpcaddr 0.0.0.0 --rpc --port 8543 --port 9991 --rpccorsdomain "*" --mine --miner.threads 1 console

geth --networkid 921 --maxpeers 2 --syncmode 'fast' --datadir dev --rpcaddr 0.0.0.0 --rpc --port 8544 --port 9992 --rpccorsdomain "*" --mine --miner.threads 1 console

## 도커 이미지로 이더리움 네트워크 구축하기.

ec2에 도커 다운로드

```jsx
sudo yum -y upgrade
sudo yum -y install docker

## 도커 버전 확인
docker -v
```

geth 도커 이미지 다운로드

```jsx
docker pull pjt3591oo/ethereum-geth:1.90
```

도커 이미지 확인

```jsx
docker images
```

컨테이너 생성

```jsx
docker run -it -name "컨테이너 이름" -p 8545:8545 -p 30303:30303 pjt3591oo/ethereum-geth:1.90 /bin/bash
```

생성 후 다음과 같은 명령어 입력

```docker
mkdir node1
cd node1
geth --datadir $PWD account new ## 현재 폴더에 계정 생성 후 비밀번호 입력
```

Address: {323584b23ee1447490ddb49674fd16a2553e6244}

pwd : 1234

해당 폴더에 `keystore` 파일 생성.

`keystore` : 비밀번호와 keystore 파일을 조합하여 개인키를 복구하는 역할

제네시스 파일 생성

`alloc` : 위에서 생성한 계정 주소 but 현재 진행 과정에서 에러가 나서 alloc 적용하지 않음

```docker
{
  "config": {
    "chainId": 921,
    "homesteadBlock": 0,
    "eip150Block": 0,
    "eip150Hash": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "eip155Block": 0,
    "eip158Block": 0,
    "byzantiumBlock": 0,
    "constantinopleBlock": 0,
    "petersburgBlock": 0,
    "istanbulBlock": 0,
    "ethash": {}

  }, 
  "alloc": {},
  "nonce": "0xdeadbeefdeadbeef",
  "difficulty": "0x10",
  "extraData": "",
  "gasLimit": "9999999"
}

{
  "config": {
    "chainId": 921,
    "homesteadBlock": 0,
    "eip150Block": 0,
    "eip150Hash": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "eip155Block": 0,
    "eip158Block": 0,
    "byzantiumBlock":0,
    "constantinopleBlock":0,
    "petersburgBlock": 0,
    "istanbulBlock": 0,
    "clique": {
     "period":2,
     "epoch":30000
    }

  },
  "alloc": {},
  "nonce": "0xdeadbeefdeadbeef",
  "difficulty": "1",
  "extraData": "0x0000000000000000000000000000000000000000000000000000000000000000f86fea9448009b4e20ec72aadf306577cbe2eaf54b0ebb1694089fcc42fd83baeee4831319375413b8bae3acebb8410000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000c0",
  "gasLimit": "9999999"
}
```

제네시스 블록을 생성하면 datadir에 명시한 해당 노드에서 데이터를 관리함

```docker
geth --datadir $PWD init genesis.json
```

- eth 접근

`sudo docker exec -it eth0 bin/bash`

networkid를 입력하는 이유는 프라이빗 네트워크를 실행할 때 필요한 추가적인 옵션이기 때문.

### 도커 컨테이너 ip주소 확인법

```docker
##docker 컨테이너 ip주소 확인
sudo docker inspect -f "{{.NetworkSettings.IPAddress}}" CONTAINER_ID
```

### 피어 연결

- 마이너 노드가 아닌 노드

```docker
admin.nodeInfo.enode
```

- 마이너 노드
    - 여기서 마이너노드의 enode를 붙여넣고 위의 결과에서 @ 뒤에 있는 ip (xxx.xxx.xxx) 를 연결할 노드의 ip 로 바꿔줘야 정상적으로 추가 됨

```docker
admin.addPeer("~~~~~") ## ~~~~~는 위의 노드의 nodeInfo의 값을 적용
```

- 피어 연결 여부 확인하는법

```docker
admin.peers
net.peerCount
```

이 아래에서부턴 기존 블록체인 방식과 동일함.

mining을 시작하면 generating 이 끊임없이 되고 있을텐데 상관없이 명령어를 입력하면 해당 명령어가 실행됨.

[테스트 네트워크 만들어보기 (by static-nodes.json)](https://dejavuqa.tistory.com/262)

### 블록체인 명령어

```docker
%%writefile src/ethCommands.js
eth.accounts; // 존재하는 계좌 리스트
eth.blockNumber; // 마이닝이 된 만큼 블록이 있음
eth.coinbase; // 현재 코인베이스
eth.getTransactionCount(eth.accounts[0]) // 거래 수

var bal = eth.getBalance(eth.accounts[0]); // 계좌의 잔액
web3.fromWei(bal, "ether"); // wei를 ether 단위로 변경

miner.start(); // 마이닝 시작
miner.stop(); // 마이닝 중지
miner.start(1);admin.sleepBlocks(1);miner.stop() // 1회만 마이닝
miner.setEtherbase(eth.accounts[1]); // coinbase 설정

admin.nodeInfo; // 노드 정보
admin.peers; // peer 정보
net.peerCount; // peer 갯수
net.listening; // peer를 찾고 있는 중인지 확인
```

# **geth 외부에서 접속**

공용 IP가 있는 서버에서 서비스를 띄우고 외부에서 접속해보자.

서버에 공용 IP가 있다면 rpcaddr에 해당 IP를 입력하면 된다.

```
#!/bin/bash

geth --datadir ./data \
     --networkid 88 \
     --rpc --rpcaddr 13.124.163.193 --rpccorsdomain "*" --rpcapi "admin,db,eth,net,web3,miner,personal" \
     --etherbase "0x99de12f3150b393c89ddccb384dd9cf8fa8414c0" \
     console

```

혹 나와 같이 가난하여 공용 IP를 가지고 있지 못하고, DDNS를 사용하여 포트 포워딩을 한다면 임시로 배정받은 공용 IP를 입력해서 서비스를 띄워 봐야  attach 시 다음과 같은 오류가 나며 연결이 되지 않는다.

이 문제는 [DNS Rebind Protetion 관련된 오류](https://github.com/ethereum/go-ethereum/pull/15962)로 이를 해결하기 위해서는 명시적으로 붙을 도메인을 다음과 같이 지정해서 서비스를 올려야 한다.

### 의문점

- 한 계정이 모든 트랜잭션을 호출하는 방법.

### admin.addPeer() 자동화하는 방법

1. pw파일 생성(vi pw)

```powershell
비밀번호
```

1. keystore가 저장된 위치에서 static-node.json파일을 생성
2. 헤드 노드를 제외한 나머지 노드들의 정보를 입력

```powershell
[
	"enode ~",
	"enode ~"
]
```

1. geth --datadir dev account new
2. geth --datadir dev init node1/genesis.json

**eth0**

"enode://4fef54d26b3512f4f9f3e0eb8ae33596b45d65e3f0c09e19098a9ec8ffeac1082aea5f8a354966fc8786e383d16f56fd01480f562073c9e75feee9d624dd1008@172.17.0.3:9991?discport=0"

**eth1**

"enode://aacd3d99fac72355bcdeac3881b89b6f840d25372cf9e762ad312bc6fc6901734a32c5ac0e404bec891681cf8895198ab92c158becb83c2acfa00d06156f0a2e@172.17.0.4:9992"

**eth2**

"enode://a112356d26685cac51e5033f18b87e679e15a74533aae1f3c26e68a99c76b56a625017f0a14f1e06fcaecb2184b00757b95d020ca850a53dbf88a21278e040f8@172.17.0.5:9993"

- 도커 컨테이너 파일 로컬에 저장하기

[Docker 컨테이너에서 깃 사용하기](https://yangeok.github.io/devops/2019/01/19/git-in-docker-container.html)