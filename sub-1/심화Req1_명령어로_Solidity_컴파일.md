# 명령어로 Solidity 컴파일

## solc 설치하기

1. https://github.com/ethereum/solidity/releases 접속 후 Tags 탭 클릭

2. solc.exe가 있는 버전은 v0.7.1 윈도우 버전에서는 `solidity-windows.zip`파일 다운로드 후 압축해제

`.\solc --version` 컴파일러 버전 확인 필

`.\solc --help 또는 .\solc -h   ` 도움말



## solc로 컴파일하기

1. 완성된 솔리디티 코드 solc.exe와 같은 폴더에 저장(remix 예제 코드 1 활용)
2. 아래 코드 입력시 `-o` 옵션으로 `path` 폴더에 `Example.sol`의 바이트코드와 abi 파일 생성 가능

```
.\solc Example.sol --bin --abi --optimize -o path
```

