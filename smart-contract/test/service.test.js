const Service = artifacts.require("Service");

contract('Service', function ([deployer, user1]) {
  beforeEach(async () => {
    service = await Service.new();
  })
  it('should return true when complete saving s.c', async () => {
    await service.registerService(user1);
  })
})
