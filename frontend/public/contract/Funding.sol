pragma solidity >=0.4.23 <0.9.0;

import "./3_Berry.sol";

contract funding is INVENToken {
    uint256 public goal;
    address public pjt;
    mapping(address => uint256) public participants;
    address public miner;

    constructor() public {
        miner = 0x0185F5D0c8304131f8a3B442d518daa548711793;
    }

    event OfferFunding(address indexed from, address to, uint256 offerPrice);
    event RegisterFunding(address indexed compAddress, uint256 goalPrice);

    function offerFunding(uint256 offerPrice) public returns (bool res) {
        participants[msg.sender] = offerPrice;

        emit OfferFunding(msg.sender, pjt, offerPrice);

        return true;
    }

    function registerFunding(address compAddress, uint256 goalPrice)
        public
        returns (bool res)
    {
        goal = goalPrice;
        pjt = compAddress;

        emit RegisterFunding(compAddress, goalPrice);

        return true;
    }
}
