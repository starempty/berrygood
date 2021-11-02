// SPDX-License-Identifier: sobin
pragma solidity >=0.4.22 <0.9.0;

contract Blood {
    struct BloodCard {
        address owner;
        uint256 cardNum;
        uint256 date;
    }

    event NEW_BLOOD_CARD(
        address indexed owner,
        uint256 cardNum,
        uint256 date
    );
    
    event MOVE_BLOOD_CARD(
        address owner, 
        address receiver, 
        uint256 indexed cardNum
    );
    mapping(uint256 => BloodCard) public cards;
    
    constructor() public { }

    /**
     * save the blood info in cards
     * @param cardNum bloodCard number
     * @param owner owner of bloodCard
     */
    function setBloodCard(address owner, uint256 cardNum) public  {
        
        cards[cardNum].owner = owner;
        cards[cardNum].cardNum = cardNum;
        cards[cardNum].date = now;
        
        
        emit NEW_BLOOD_CARD(owner, cardNum, now);
    
    }

    function getCard(uint256 num)
        public
        view
        returns (
            address owner,
            uint256 cardNum,
            uint256 date
        )
    {
        owner = cards[num].owner;
        cardNum = cards[num].cardNum;
        date = cards[num].date;
    }
    
    function sendCard(address owner, address receiver, uint256 cardNum) public returns(bool result){
        require(cards[cardNum].owner == owner);
        cards[cardNum].owner = receiver;
        
        emit MOVE_BLOOD_CARD(owner, receiver, cardNum);        
        
        return true;
    }
}
