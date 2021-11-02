const Blood = artifacts.require("Blood");

contract('Blood', function ([deployer]) {
  beforeEach(async () => {
    blood = await Blood.new();
  })
  it('should return true when complete saving b.c', async () => {
    await blood.setBloodCard(deployer, 123, "적혈", "2021-09-10");
    let data = await blood.getCard();
    console.log(data);
  })
})
