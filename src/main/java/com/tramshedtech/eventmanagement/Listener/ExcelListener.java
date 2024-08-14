package com.tramshedtech.eventmanagement.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tramshedtech.eventmanagement.entity.UserExcel;
import com.tramshedtech.eventmanagement.mapper.ExcelMapper;


import java.util.ArrayList;
import java.util.List;


public class ExcelListener extends AnalysisEventListener<UserExcel> {

    private List<UserExcel> datas = new ArrayList<>();
    private static final int BATCH_COUNT = 3000;
    private ExcelMapper excelMapper;

    public ExcelListener(ExcelMapper excelMapper){
        this.excelMapper = excelMapper;
    }

    @Override
    public void invoke(UserExcel userExcel, AnalysisContext analysisContext) {
        // Data is stored to datas for batch processing, or subsequent processing by your own business logic.
        datas.add(userExcel);
        //Reach BATCH_COUNT, need to go to the database to store once, to prevent data tens of thousands of data in memory, easy to OOM!
        if(datas.size() >= BATCH_COUNT){
            saveData();
            // Storage completion cleanup datas
            datas.clear();
        }

    }

    private void saveData() {
        for(UserExcel userExcel : datas){
            this.excelMapper.insert(userExcel);
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }
}
