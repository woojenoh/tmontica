import { ICartMenuOption, TBasicMenuOptionArray, TMenuOptionMap, TMenuOption } from "./types";

// 숫자를 천 단위로 콤마 찍어주는 함수.
export const numberCommaRegex = (number: number | string): string => {
  return String(number).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

// 객체 상태의 옵션을 정해진 스트링 형식으로 바꾸는 함수.
export const optionToString = (option: ICartMenuOption): string => {
  let stringArray = [];
  option.Temperature && stringArray.push(`${option.Temperature.name}`);
  option.Shot && stringArray.push(`샷추가(${option.Shot.amount}개)`);
  option.Syrup && stringArray.push(`시럽추가(${option.Syrup.amount}개)`);
  option.Size && stringArray.push(`사이즈업`);
  if (stringArray.length === 0) {
    return "옵션이 없습니다.";
  } else {
    return stringArray.join("/");
  }
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
  const newOption = Array.from(option.values()).map(o => {
    return { id: o.id, quantity: o.quantity };
  });
  return {
    menuId,
    quantity,
    direct,
    option: newOption
  };
};
