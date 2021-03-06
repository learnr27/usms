export class User {
  // id
  id: number;
  // 登入名
  loginName: string;
  // 姓名
  name: string;
  // 状态
  enabled: number;
  // 备注说明
  remarks: string;
  // 固定电话
  tel: string;
  // 邮编
  zipCode: string;
  // 手机电话
  mobile: string;
  // 内网邮箱
  email: string;
  // 外网邮箱
  extranetEmail: string;
  // 别名
  aliasNames: string;
  // 性别
  sex: number;
  // 职称
  professionalTitle: string;
  // 职责
  officalPost: string;
  // 职务
  officalDuty: string;
  // 员工类型
  employeeType: string;
  // 员工工号
  employeeNo: string;
  // 出生日期
  birthday: number;
  // 居住地行政区划编号
  adminDivisionCode: string;
  //居住地行政区划
  adminDivision: string;
  // 现居住地址
  curResidence: string;
  // 身份证号码
  citizenIdNumber: string;
  // 照片路径
  pictureUrl: string;
  // 关联的角色id
  roleIds: string;
  // 关联的组织机构id
  institutionIds: string;
  // 关联的组织机构名称
  institutionNames: string[];
  // 关联的网格名称
  gridNames: string[];
}
