package mapper;

import po.TestApply;

public interface TestMapper {
    void save(TestApply apply);
    void update(TestApply apply);
    TestApply get(int id);
}
