import * as React from 'react'

export default class LastAttemptComponent extends React.Component<any, any> {
  render() {
    return (
      <table>
        <thead>
          <tr>
            <th>Challenge</th>
            <th>Your guess</th>
            <th>Correct</th>
          </tr>
        </thead>
        <tbody>
          {this.props.lastAttempts.map(a =>
            <tr key={a.id}
              style={{ color: a.correct ? 'green' : 'red' }}>
              <td>{a.factorA} x {a.factorB}</td>
              <td>{a.resultAttempt}</td>
              <td>{a.correct ? "Correct" :
                ("Incorrect (" + a.factorA * a.factorB + ")")}</td>
            </tr>
          )}
        </tbody>
      </table>
    )
  }
}
