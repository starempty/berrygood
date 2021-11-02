const Cash = artifacts.require("Cash");
const { assert } = require("chai");

contract('Cash', function ([deployer, user1]) {
  beforeEach(async () => {
    cash = await Cash.new();
  })

  it('totalSupply should return current total, 5 * 10**18', async () => {
    let total = await cash.totalSupply();
    assert.equal(total, 5 * 10 ** 18);
  })
  it('buy should return true when complete adding values to balance', async () => {
    let receipt = await cash.buy({ from: deployer, to: user1, value: 1e18 });
    console.log(receipt);
  })
  it.only('should return result of saving', async () => {
    await cash.donation(user1, { from: deployer, to: user1, value: 1e15 });
    let balance = await cash.getDbalance(user1);
    assert.equal(balance, 1e15);
  })
})

