package com.ezdata.commonlib.data;


//import com.ezdata.xcqframeopencv.model.DownloadModel;
//import com.ezdata.xcqframeopencv.model.PersonModel;
//import com.ezdata.xcqframeopencv.model.PrisonAreaModel;
//import com.ezdata.xcqframeopencv.model.UnitModel;
//import com.ezdata.xcqframeopencv.model.UpdateModel;
//import com.ezdata.xcqframeopencv.model.UserModerl;

/**
 * 业务数据处理类
 * Created by MSI-PC on 2018/4/3.
 */

public class DataManager {
    private static DataManager dataManager;
//    private UnitModel unitModel;
//    private PersonModel personModel;
//    private PrisonAreaModel prisonAreaModel;
//    private UserModerl userModerl;
//    private UpdateModel updateModel;
//    private DownloadModel downloadModel;

    public synchronized static DataManager getInstance () {
        if (dataManager == null) {
            dataManager = new DataManager ();
        }
        return dataManager;
    }
    private DataManager() {

//        unitModel = new UnitModel();
//        personModel = new PersonModel();
//        prisonAreaModel = new PrisonAreaModel();
//        userModerl = new UserModerl();
//        updateModel = new UpdateModel();
//        downloadModel = new DownloadModel();

    }
//    public UnitModel getUnitModel(){return unitModel;}
//    public PersonModel getPersonModel(){return personModel;}
//    public PrisonAreaModel getPrisonAreaModel(){return prisonAreaModel;}
//    public UserModerl getUserModerl(){return userModerl;}
//    public UpdateModel getUpdateModel(){return updateModel;}
//    public DownloadModel getDownloadModel(){return downloadModel;}
}
