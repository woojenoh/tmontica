export const API_URL = "http://tmontica-idev.tmon.co.kr/api";

async function fetchTMON(url: string, options: RequestInit) {
  const res = await fetch(url, options);

  const json = await res.json();
  if (res.ok) {
    return json;
  }

  const err = json as {
    timestamp: string;
    status: number;
    error: string;
    message: string;
    path: string;
  };

  throw new Error(err.message);
}

export function get(reqURL: string, params?: Map<string, string> | null, jwt?: string) {
  if (typeof params !== "undefined" && params !== null && !/[?]/.test(reqURL)) {
    reqURL += `?${Array.from(params.entries())
      .map(x => {
        return `${x[0]}=${x[1]}`;
      })
      .join("&")}`;
  }

  return fetchTMON(reqURL, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: jwt || ""
    },
    method: "GET"
  });
}

export function getWithJWT(reqURL: string, params?: Map<string, string> | null) {
  const jwt = localStorage.getItem("jwt") || "";

  return get(reqURL, params, jwt);
}

export function postWithJWT(reqURL: string, data: any) {
  const jwt = localStorage.getItem("jwt") || "";

  return fetchTMON(reqURL, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: jwt
    },
    method: "POST",
    body: JSON.stringify(data)
  });
}
