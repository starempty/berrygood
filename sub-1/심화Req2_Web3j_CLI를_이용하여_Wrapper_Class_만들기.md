## Web3j CLI를 이용하여 Wrapper Class 만들기

### web3j cli 설치 (powershell에서 진행)

`Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://raw.githubusercontent.com/web3j/web3j-installer/master/installer.ps1'))`

`web3j version` web3j 버전 확인(안되면 shell창 재부팅 후 재시도.)

### Wrapper Class 생성하기

`web3j generate solidity --abiFile=ptimize/Storage.abi, --outputDir=ptimize, --package=ssafy`

위 명령어 실행 시 [명령 수행한 폴더 경로]/ptimize/ssafy 폴더 내 solidity파일과 동일한 이름의 Wrapper Class 생성

