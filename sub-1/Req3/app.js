const path = require('path');
const fs = require('fs');
const solc = require('solc');

const contractPath = path.resolve(__dirname, 'contracts', 'Example.sol');
const storageContract = fs.readFileSync(contractPath, 'UTF-8');
var input = {
    language: 'Solidity',
    sources:{
        'Example.sol':{
            content: storageContract
        }
    },
    settings:{
        outputSelection:{
            '*':{
                '*':['*']
            }
        }
    }
};
var output = JSON.parse(solc.compile(JSON.stringify(input)))
console.log(output)
var bin = '0x'+output.contracts['Example.sol']['Storage'].evm.bytecode.object;
var abi = JSON.stringify(output.contracts['Example.sol']['Storage'].abi);
console.log('bytecode: '+bin);
console.log('abi: '+ abi);

Web3 = require('web3')

var web3 = new Web3(new Web3.providers.HttpProvider('http://localhost:8545'));
const contractFactory = new web3.eth.Contract(JSON.parse(abi));
console.log(contractFactory)
web3.eth.estimateGas({data: bin}).then(gasEstimated => {
    web3.eth.getAccounts().then(accounts => {
        var sender = accounts[0];

        const contractInstance = contractFactory.deploy({
            data: bin
        })
        .send({
            from: sender,
            gas: gasEstimated
        }, function(e, transactionHash){
            console.log("e, transaction")
            console.log(e);
            console.log(transactionHash);
        })
        .on('error', function(error){
            console.error
        })
        .on('transactionHash', (transactionHash, receipt) => {
            console.log("onTransactionHash")
            console.log(transactionHash)
            console.log(receipt)
        })
        .on('receipt', (receipt) => {
            console.log("onReceipt")
            console.log(receipt.contractAddress)
        })
        .on('confirmation', function(confirmationNumber, receipt){
            console.log(confirmationNumber)
            console.log(receipt)
        })
        .then(function(newContractInstance){
            console.log("newContractInstance")
            console.log(newContractInstance.options.address)
        });
    })
});