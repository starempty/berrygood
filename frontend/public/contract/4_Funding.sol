pragma solidity >=0.4.22 <0.9.0;

import "./3_Berry.sol";

contract funding is INVENToken{
    struct fundNode{
        address participant;
        uint256 price;
    }
    uint256 public goal;
    address public pjt;
    uint256 public amount;
    mapping(uint256 => fundNode) public participants;
    uint256 cnt;
    uint public due;
    address miner;
    
    constructor() public {
        miner = 0x0185F5D0c8304131f8a3B442d518daa548711793;
    }
    
    event OfferFunding(address indexed from, address to, uint256 offerPrice);
    event RegisterFunding(address indexed compAddress, uint256 goalPrice, uint256 due);

    function offerFunding(uint256 offerPrice) external returns (bool res){
        fundNode memory fn;
        fn.participant = msg.sender;
        fn.price = offerPrice;
        participants[cnt] = fn;
        cnt += 1;
        
        transfer(miner, offerPrice);
        
        emit OfferFunding(msg.sender, pjt, offerPrice);
        
        return true;
    }
    function registerFunding(address compAddress, uint256 goalPrice, uint256 duration) external returns(bool res){
        goal = goalPrice;
        pjt = compAddress;
        due = duration;
        amount = 0;
        cnt = 0;
        
        emit RegisterFunding(compAddress, goalPrice, due);
        
        return true;
    }
    /**
     * call by miner
     * @return total total_amount of funding, 0 if funding canceled
     */
    function checkGoal() external returns(uint256 total){
        // require(block.timestamp >= due);
        
        if(amount >= goal){
            transfer(pjt, amount);
            return amount;
        }
        else{
            for(uint i = 0; i < cnt; i++){
                transfer(participants[i].participant, participants[i].price);
            }
            return 0;
        }
    }
}
