package com.wzh.lab.lol.enums;

/**
 * <p>
 * 代码描述
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/24 22:31
 */
public enum PieceEnum {

    /**
     *
     */
    CUISITE("崔斯特","1/1"),
    CUISITANA("崔丝塔娜","1/2"),
    MAOKAI("茂凯","1/3"),
    YILISI("伊莉丝","1/4"),
    SUNWUKONG("孙悟空","1/5"),
    BULANDE("布兰德","1/6"),
    NEISESI("内瑟斯","1/7"),
    NAIDELI("奈德丽","1/8"),
    GAILUN("盖伦","1/9"),
    FEIAOAN("菲奥娜","1/10"),
    DAIANNA("黛安娜","1/11"),
    YASUO("亚素","1/12"),
    TAMU("塔姆","1/13"),

    /**
     *
     */
    ANNE("安妮", "2/1"),

    VLADIMIR("弗拉基米尔", "2/2"),

    TEEMO("提莫", "2/3"),

    JAX("贾克斯", "2/4"),

    JANA("迦娜", "2/5"),

    JAWEN("嘉文四世", "2/6"),

    NUOLE("诺提勒斯", "2/7"),

    LULU("璐璐", "2/8"),

    BULONG("布隆", "2/9"),

    JIE("劫", "2/10"),

    WEI("蔚", "2/11"),

    LUO("洛", "2/12"),

    PAIKE("派克", "2/13"),

    XIWEIER("希维尔", "3/1"),
    NUNU("努努和威朗普", "3/2"),
    ARUILIYA("艾瑞莉娅", "3/3"),
    WEIGA("维迦", "3/4"),
    KATLINNA("卡特琳娜", "3/5"),
    ALALI("阿卡丽", "3/6"),
    KAINA("凯南", "3/7"),
    XIWANA("希瓦娜", "3/8"),
    DELAIESI("德莱厄斯", "3/9"),
    QIANJUE("千珏", "3/10"),
    YOUMI("悠米", "3/11"),
    KALISITA("卡莉丝塔", "3/12"),
    NIKO("妮蔻", "3/13"),

    AOLAFU("奥拉夫","4/1"),
    KAIER("凯尔","4/2"),
    TAIDAMIER("泰达米尔","4/3"),
    MAGANNA("莫甘娜","4/4"),
    KAJIASI("科加斯","4/5"),
    TAILONG("泰隆","4/6"),
    SHEN("慎","4/7"),
    SEZHUANGNI("瑟庄妮","4/8"),
    SUOER("奥瑞利安 ° 素尔","4/9"),
    YATUOKESI("亚托克斯","4/10"),
    XIA("霞","4/11"),

    JILAN("基兰","5/1"),
    SIWEIYIN("斯维因","5/2"),
    LIQING("李青","5/3"),
    AZIER("阿兹尔","5/4"),
    SHANILA("莎弥拉","5/5"),
    AOEN("奥恩","5/6"),
    YONGEN("永恩","5/7"),
    SETI("瑟提","5/8"),






    ;

    private String name;
    private String code;

    PieceEnum(String name, String code) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
