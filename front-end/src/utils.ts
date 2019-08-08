import { TMenuOptionMap } from "./types/menu";
import { ICartMenuOption } from "./types/cart";
import history from "./history";

export const goToSignin = (message = "로그인이 필요합니다.") => {
  alert(message);
  history.push("/signin");
};

// 숫자를 천 단위로 콤마 찍어주는 함수.
export const numberCommaRegex = (number: number | string): string => {
  return Number(number).toLocaleString();
};

// 객체 상태의 옵션을 정해진 스트링 형식으로 바꾸는 함수.
export const optionToString = (option: TMenuOptionMap): string => {
  let stringArray = [];
  const temperature = option.get("Temperature");
  temperature && stringArray.push(`${temperature.name}`);
  const shot = option.get("Shot");
  shot && stringArray.push(`샷추가(${shot.quantity}개)`);
  const syrup = option.get("Syrup");
  syrup && stringArray.push(`시럽추가(${syrup.quantity}개)`);
  const size = option.get("Size");
  size && stringArray.push(`사이즈업`);
  if (stringArray.length === 0) {
    return "옵션이 없습니다.";
  } else {
    return stringArray.join("/");
  }
};

export const convertOptionMapToArray = (oMap: TMenuOptionMap): ICartMenuOption[] => {
  return Array.from(oMap.values()).map(o => {
    return { id: o.id, quantity: o.quantity };
  });
};

export const createCartAddReq = ({
  menuId,
  quantity,
  option,
  direct
}: {
  menuId: number;
  quantity: number;
  option: TMenuOptionMap;
  direct: boolean;
}) => {
  const newOption = convertOptionMapToArray(option);
  return {
    menuId,
    quantity,
    direct,
    option: newOption
  };
};

// export function handleInputChangeCommon(e: ChangeEvent<HTMLInputElement>) {
//   const self = this as React.PureComponent;
//   const target = e.target;
//   const value = target.type === "checkbox" ? target.checked : target.value;
//   const name = target.name;
//   self.setState({
//     [name]: value
//   });
// }
