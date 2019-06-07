package service;

import po.TbAttence;
import po.query.AttenceData;

import java.util.List;

public interface AttenceService {
    List<TbAttence> getMyAttence(int userId, int firstRow, int rowCount);

    int getMyAttenceCount(int userId);

    void addAttence(List<TbAttence> attenceList);

    List<TbAttence> getAllAttence(int firstRow, int rowCount);

    int getAllAttenceCount();

    List<AttenceData> getAttenceDatas(String status, String start, String end);

    List<AttenceData> getAllAttenceDatas(String start, String end);

    AttenceData getUserAttenceData(String start, String end, String name);
}
