// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.4.22 <0.9.0;

import "../smart-contracts/SafeMath.sol";

interface IERC20 {
    function totalSupply() external view returns (uint256);

    function balanceOf(address account) external view returns (uint256);

    function transfer(address recipient, uint256 amount)
        external
        returns (bool);

    function allowance(address owner, address spender)
        external
        view
        returns (uint256);

    function approve(address spender, uint256 amount) external returns (bool);

    function transferFrom(
        address sender,
        address recipient,
        uint256 amount
    ) external returns (bool);

    event Transfer(address indexed from, address indexed to, uint256 value);
    event Approval(
        address indexed owner,
        address indexed spender,
        uint256 value
    );
}

/**
 * @notice Cash follows the ierc20 standard and is used in the donation and funding service.
 */
contract Cash is IERC20 {
    using SafeMath for uint256;

    string public constant name = "Berry";
    string public constant symbol = "B";

    struct Fund {
        address giver;
        uint256 amount;
    }

    address payable public minter;
    mapping(address => uint256) private balances;
    mapping(address => mapping(address => uint256)) private allowed;
    mapping(address => uint256) private dbalances;
    mapping(address => uint256) private fbalances;
    mapping(address => mapping(uint256 => Fund)) private fHistory;
    mapping(address => uint256) private _ftail;

    uint256 public total;

    /**
     * @notice constructor
     * total(inital supply), minter
     */
    constructor() public {
        total = 5 * 10**18;
        minter = msg.sender;
        balances[minter] = total;
    }

    event DonateEvent(address giver, uint256 amount, uint256 balance);
    event FundingEvent(address giver, address pjt, uint256 amount);
    event SuccessFunding(address pjt, uint256 amount);
    event RefundFunding(address pjt, uint256 participants);

    function funding(address payable pjt) public payable returns (bool) {
        require(pjt != msg.sender);
        require(msg.value > 0);
        require(balances[msg.sender] >= msg.value, "check balance");
        balances[msg.sender] = balances[msg.sender].sub(msg.value);
        balances[pjt] = balances[pjt].add(msg.value);

        fbalances[pjt] = fbalances[pjt].add(msg.value);

        fHistory[pjt][_ftail[pjt]].giver = msg.sender;
        fHistory[pjt][_ftail[pjt]].amount = msg.value;
        _ftail[pjt]++;

        emit FundingEvent(msg.sender, pjt, msg.value);
    }

    /**
     * check funding amount for giving
     * server will be sent signal for function (time issue)
     * @param pjt funding pjt
     * @param goal goal amount of money
     * @return res should return true if payback to anyware
     */
    function checkFunding(address payable pjt, uint256 goal)
        public
        payable
        returns (bool res)
    {
        //send berry to pjt
        if (goal <= fbalances[pjt]) {
            uint256 amount = fbalances[pjt];
            balances[pjt] += amount;
            fbalances[pjt] -= amount;
            emit SuccessFunding(pjt, amount);
        } else {
            //refund
            uint256 cur;
            for (cur = 0; cur < _ftail[pjt]; cur++) {
                address owner = fHistory[pjt][cur].giver;
                uint256 amount = fHistory[pjt][cur].amount;
                balances[owner] = balances[owner].add(amount);
            }
            emit RefundFunding(pjt, _ftail[pjt]);
        }
        return true;
    }

    /**
     * send money for donation pjt
     * @param pjt pjt address that giver donate
     */
    function donation(address payable pjt)
        public
        payable
        returns (bool result)
    {
        require(pjt != msg.sender, "cannot donate to myself");
        require(msg.value > 0);
        require(balances[msg.sender] >= msg.value, "check balance");
        balances[msg.sender] = balances[msg.sender].sub(msg.value);
        balances[pjt] = balances[pjt].add(msg.value);

        dbalances[pjt] += msg.value;
        emit DonateEvent(msg.sender, msg.value, balances[pjt]);

        return true;
    }

    function getDbalance(address pjt) external view returns (uint256 balance) {
        balance = dbalances[pjt];
    }

    /**
     * @notice mint
     * @param _receiver the receiver's address
     * @param _amount the amount of tokens
     */
    function mint(address _receiver, uint256 _amount) external {
        require(msg.sender == minter);
        require(_amount < 1e60);
        balances[_receiver] = balances[_receiver].add(_amount);
        total = total.add(_amount);
    }

    /**
     * @return amount of tokens in existence
     */
    function totalSupply() external view returns (uint256) {
        return total;
    }

    /**
     * @notice retrieves the balance that the account has
     * @param _account address
     * @return balance
     */
    function balanceOf(address _account) external view returns (uint256) {
        return balances[_account];
    }

    /**
     * @notice transfers the amount of token to the recipient
     * @param _recipient the receiver's address
     * @param _amount the amount of tokens
     * @return success or failure
     */
    function transfer(address _recipient, uint256 _amount)
        external
        returns (bool)
    {
        require(
            _amount > 0 && balances[msg.sender] >= _amount,
            "Insufficient balance."
        );
        balances[msg.sender] = balances[msg.sender].sub(_amount);
        balances[_recipient] = balances[_recipient].add(_amount);
        emit Transfer(msg.sender, _recipient, _amount);
        return true;
    }

    /**
     * @notice retrieves the delegated balance
     * @param _owner the onwer's address
     * @param _spender the delegator's address
     * @return the amount of allownace
     */
    function allowance(address _owner, address _spender)
        external
        view
        returns (uint256)
    {
        return allowed[_owner][_spender];
    }

    /**
     * @notice delegate the transfer
     * @param _spender the delegator's address
     * @param _amount the allowed amount of tokens
     * @return success or failure
     */
    function approve(address _spender, uint256 _amount)
        external
        returns (bool)
    {
        require(
            _amount > 0 && balances[msg.sender] >= _amount,
            "Insufficient balance."
        );
        allowed[msg.sender][_spender] = allowed[msg.sender][_spender].add(
            _amount
        );
        emit Approval(msg.sender, _spender, _amount);
        return true;
    }

    /**
     * @notice transfers the amount of token on behalf of the owner
     * @param _sender the sender's address
     * @param _recipient the receiver's address
     * @param _amount the amount of tokens
     * @return success of failure
     */
    function transferFrom(
        address _sender,
        address _recipient,
        uint256 _amount
    ) external returns (bool) {
        require(_amount > 0, "Wrong amount of cash.");
        require(balances[_sender] >= _amount, "Insufficient balance.");
        require(
            allowed[_sender][msg.sender] >= _amount,
            "Insufficient allowance"
        );

        allowed[_sender][msg.sender] = allowed[_sender][msg.sender].sub(
            _amount
        );
        balances[_sender] = balances[_sender].sub(_amount);
        balances[_recipient] = balances[_recipient].add(_amount);
        emit Transfer(msg.sender, _recipient, _amount);
        return true;
    }

    /**
     * @notice buy tokens
     * msg.value should be greater than or equal to 0.1 ether
     * 1 eth = 100,000 cash
     * @return success or failure
     */
    function buy() public payable returns (bool) {
        require(msg.sender != minter);
        require(msg.value >= 0.1 ether, "greater than or equal to 0.1 ether");

        uint256 _amount = (msg.value * 100000) / (10**18);

        require(balances[minter] >= _amount, "Insufficient balance");

        minter.transfer(msg.value);
        balances[minter] = balances[minter].sub(_amount);
        balances[msg.sender] = balances[msg.sender].add(_amount);
        emit Transfer(minter, msg.sender, _amount);

        return true;
    }
}
