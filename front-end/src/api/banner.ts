import { get, API_URL } from "./common";
import { IBanner } from "../types/banner";

// const multipartHeader = {
//   headers: { "content-type": "multipart/form-data" }
// };

export function getBannerByUsePageEng(usePageEng: string) {
  return get<IBanner[]>(`${API_URL}/banners/${usePageEng}`);
}
