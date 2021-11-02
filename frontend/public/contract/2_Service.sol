// SPDX-License-Identifier: sobin
pragma solidity >=0.4.22 <0.9.0;

contract Service {
    mapping(uint256 => mapping(address => bool)) serveList;

    event RegisteredService(address indexed server, uint256 serveNo);
    event CancelService(address indexed server, uint256 serveNo);

    constructor() public {}

    function registerService(uint256 serveNo) public returns (bool res) {
        serveList[serveNo][msg.sender] = true;

        emit RegisteredService(msg.sender, serveNo);

        return true;
    }

    function cancelService(uint256 serveNo) public returns (bool res) {
        serveList[serveNo][msg.sender] = false;

        emit CancelService(msg.sender, serveNo);

        return true;
    }
}
