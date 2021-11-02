pragma solidity >=0.4.22 <0.9.0;

contract Service {
    struct ServeCard {
        address server;
        address pjt;
    }
    mapping(address => mapping(uint256 => ServeCard)) serveCards;
    mapping(address => uint256) serveCount;

    event Service(address server, address pjt);

    constructor() public {}

    function registerService(address pjt) public returns (bool res) {
        require(msg.sender != pjt);
        uint256 count = serveCount[pjt];
        ServeCard memory s;
        s.server = msg.sender;
        s.pjt = pjt;
        serveCards[pjt][count] = s;

        emit Service(msg.sender, pjt);

        return true;
    }
}
