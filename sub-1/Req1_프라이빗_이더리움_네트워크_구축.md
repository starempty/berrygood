# 프라이빗 이더리움 네트워크 구축

<br/>
<br/>

## 가상 머신 구성

목표 : VirtualBox, Vagrant설치 및 이더리움 네트워크 구축용 VM 2대 생성

<br/>

VirtualBox, Vagrant설치가 되었다고 가정하고 진행하겠습니다.

1. vargrant version : 설치 여부 및 버전 확인

2. vagrant plugin install vagrant-scp : 호스트와 가상 머신 간 파일 전송 플로그인 설치

![](Req1/vagrant_plugin%20install_vagrant-scp.png)

3. vargrant init : 작업 디렉토리에서 Vagrant 초기화 (Vagrantfile 생성)

4. 생성된 Vagrantfiel 내용 수정

``` bash
VAGRANT_API_VERSION ="2"

vms = {
  'eth0' => '10',
  'eth1' => '11'
}

Vagrant.configure(VAGRANT_API_VERSION) do |config|
  config.vm.box = "ubuntu/bionic64"
  vms.each do |key, value|
    config.vm.define "#{key}" do |node|
      node.vm.network "private_network", ip: "192.168.50.#{value}"
      if "#{key}" == "eth0"
        node.vm.network "forwarded_port", guest: 8545, host: 8545
      end
      node.vm.hostname = "#{key}"
      node.vm.provider "virtualbox" do |nodev|
        nodev.memory = 2048
      end
    end
  end
end
```

5. vagrant up : 가상머신 구동

6. vagrant status :  가상머신 상태 확인

![](Req1/vagrant_status.png)

7. 가상 머신 접속
   - vagrant ssh eth0
   - vagrant ssh eth1

<br/>
<br/>

## 이더리움 노드 구성

<br/>

가상머신 접속 후

1. geth 설치

``` bash
sudo apt-get update
sudo apt-get install software-properties-common
sudo add-apt-repository -y ppa:ethereum/ethereum
sudo apt-get install ethereum -y
```

2. genesis.json 파일 생성 ([genesis.json 자세한내용](https://developer-mac.tistory.com/42?category=305312))

``` json
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
```

3. mkdir -p dev/eth_localdata (keystore 등을 저장할 폴더)