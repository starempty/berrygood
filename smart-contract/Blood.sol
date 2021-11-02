pragma solidity >=0.4.22 <0.9.0;

contract Blood {
    struct BloodCard {
        address owner;
        uint256 cardNum;
        string kind;
        string date;
    }

    event Blood(address owner, uint256 cardNum, string kind, string date);

    uint256 private _cnt;
    mapping(uint256 => BloodCard) public cards;

    constructor() public {
        _cnt = 0;
    }

    /**
     * save the blood info in cards
     * @param owner owner of bloodcard
     * @param cardNum bloodCard number
     * @param kind type of blood
     * @param date date of bloodCard
     */
    function setBloodCard(
        address owner,
        uint256 cardNum,
        string memory kind,
        string memory date
    ) public returns (bool result) {
        require(msg.sender == owner, "only owner can save card");
        BloodCard memory b;
        b.owner = owner;
        b.cardNum = cardNum;
        b.kind = kind;
        b.date = date;

        cards[_cnt] = b;
        _cnt++;

        emit Blood(owner, cardNum, kind, date);
        return true;
    }

    function getCard()
        public
        view
        returns (
            address owner,
            uint256 cardNum,
            string memory kind,
            string memory date
        )
    {
        BloodCard memory b = cards[_cnt - 1];
        owner = b.owner;
        cardNum = b.cardNum;
        kind = b.kind;
        date = b.date;
    }
}
