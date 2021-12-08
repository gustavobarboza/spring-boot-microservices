/**
 * An Api client used to access the application APIs
 */
export default class ApiClient {
  static SERVER_URL = "http://localhost:8080";
  static GET_CHALLENGE = "/challenges/random";
  static POST_RESULT = "/attempts";
  static GET_ATTEMPTS_BY_ALIAS = "/attempts?alias="

  static challenge(): Promise<Response> {
    return fetch(ApiClient.SERVER_URL + ApiClient.GET_CHALLENGE);
  }

  static sendGuess(
    user: string,
    a: number,
    b: number,
    guess: number
  ): Promise<Response> {
    return fetch(ApiClient.SERVER_URL + ApiClient.POST_RESULT, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userAlias: user,
        factorA: a,
        factorB: b,
        guess: guess,
      }),
    });
  }

  static getAttempts(userAlias: string) {
    return fetch(`${ApiClient.SERVER_URL}${ApiClient.GET_ATTEMPTS_BY_ALIAS}${userAlias}`)
  }
}
