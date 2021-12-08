import { Component } from "react";
import ApiClient from "../services/ApiClient";
import CurrentChallenge from "./CurrentChallengeComponent";
import LastAttemptComponent from "./LastAttemptsComponent";
export default class ChallengeComponent extends Component<any, any> {
  constructor(props: any) {
    super(props);

    this.state = {
      a: "",
      b: "",
      user: "",
      message: "",
      guess: 0,
      lastAttempts: []
    };
  }

  componentDidMount() {
    this.refreshChallenge();
  }

  handleChange = ({ target: currentTarget }: any) => {
    const name = currentTarget.name;
    this.setState({
      [name]: currentTarget.value,
    });
  };

  handleSubmitResult = (event: any) => {
    event.preventDefault();
    ApiClient.sendGuess(
      this.state.user,
      this.state.a,
      this.state.b,
      this.state.guess
    ).then((res) => {
      if (res.ok) {
        res.json().then((json) => {
          if (json.correct) {
            this.updateMessage("Congratulations! Your guess is correct");
          } else {
            this.updateMessage(
              "Oops! Your guess " + json.resultAttempt + " is wrong, but keep playing!"
            );
          }

          this.updateLastAttempts(this.state.user)
          this.refreshChallenge()
        });
      } else {
        this.updateMessage("Error: server error or not available");
      }
    });
  }

  private refreshChallenge() {
    ApiClient.challenge().then((res) => {
      if (res.ok) {
        res.json().then((json) => {
          this.setState({ a: json.factorA, b: json.factorB });
        });
      } else {
        this.updateMessage("Cannot reach the server");
      }
    });
  }

  updateLastAttempts(userAlias: string) {
    ApiClient.getAttempts(userAlias).then(res => {
      if (res.ok) {
        let attempts: Array<any> = []
        res.json().then(data => {
          data.forEach(item => attempts.push(item))
          this.setState({ lastAttempts: attempts })
        })
      }
    })
  }

  updateMessage = (msg: string) => {
    this.setState({
      message: msg,
    });
  };

  render() {
    return (
      <div className="display-column">
        <CurrentChallenge sum={{ a: this.state.a, b: this.state.b }} />
        <form onSubmit={this.handleSubmitResult}>
          <label>
            Your alias:
            <input type="text" maxLength={12}
              name="user"
              value={this.state.user}
              onChange={this.handleChange} />
          </label>
          <br />
          <label>
            Your guess:
            <input type="number" min="0"
              name="guess"
              value={this.state.guess}
              onChange={this.handleChange} />
          </label>
          <br />
          <input type="submit" value="Submit" />
        </form>
        <h4>{this.state.message}</h4>
        <span>Attempts size: {this.state.lastAttempts.length}</span>
        {this.state.lastAttempts.length > 0 &&
          <LastAttemptComponent lastAttempts={this.state
            .lastAttempts} />
        }
      </div>
    )
  }
}
