//package gaia3d.utils;
//
//public class Addrequiredfields {
//    /**
//     * 初始化ao
//     *
//     * @param aoInit
//     * @return
//     */
//    public AoInitialize initializeEngine(AoInitialize aoInit) {
//        try {
//            EngineInitializer.initializeEngine();
//
//            // 设置使用的arcgis产品和版本，使ao能运行在不同的arcgis环境下
//            // 下面这行代码会报错，可是VersionManager又要在AoInitialize实例化前运行，而且这个错不影响代码运行，所以暂时不管
//            VersionManager versionManager = new VersionManager();
//            // 第一个参数是arcgis产品编号：1=desktop，2=engine，5=server
//            // 此参数可以通过枚举查看esriProductCode
//            // versionManager.loadVersion(1, "10.0");
//            versionManager.loadVersion(Integer.parseInt(ConstansURL
//                    .getApplicationPropertyByKey("aoRuntimeProduct")), ConstansURL
//                    .getApplicationPropertyByKey("aoRuntimeVersion"));
//
//            aoInit = new AoInitialize();
//            // 下面虽然有Engine和ArcInfo两种产品，但在本系统只有Engine才能用，ArcInfo会出错
//            if (aoInit
//                    .isProductCodeAvailable(esriLicenseProductCode.esriLicenseProductCodeEngine) == esriLicenseStatus.esriLicenseAvailable) {
//                aoInit.initialize(esriLicenseProductCode.esriLicenseProductCodeEngine);
//            } else if (aoInit
//                    .isProductCodeAvailable(com.esri.arcgis.system.esriLicenseProductCode.esriLicenseProductCodeArcInfo) == com.esri.arcgis.system.esriLicenseStatus.esriLicenseAvailable) {
//                aoInit.initialize(esriLicenseProductCode.esriLicenseProductCodeArcInfo);
//            }
//            return aoInit;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return aoInit;
//        }
//    }
//    /***
//     * @Description
//     * @Param [
//     * pFieldsEdit,  IFieldEdit编辑字段对象
//     * fieldName,  字段名字
//     * AliasName,  字段说明
//     * fieldLength, 字段长度
//     * fieldType  字段类型
//     * ]
//     * @return void
//     *
//      * @Description //设置属性字段
//      * @Param [IFeatureClass, string, string, esriFieldType]
//      * @Author LTW
//      * @Date 2023/6/21
//     */
//    private void AddField(IFeatureClass pFeatureClass, string name, string aliasName, esriFieldType FieldType)
//    {
//        //若存在，则不需添加
//        if(pFeatureClass.Fields.FindField(name) > -1) return ;
//        IField pField = new FieldClass();
//        IFieldEdit pFieldEdit = pField as IFieldEdit;
//        pFieldEdit.AliasName_2 = aliasName;
//        pFieldEdit.Name_2 = name;
//        pFieldEdit.Type_2 = FieldType;
//
//        IClass pClass = pFeatureClass as IClass;
//        pClass.AddField(pField);
//    }
//}
