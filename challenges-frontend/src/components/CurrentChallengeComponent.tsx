const CurrentChallenge = ({sum}) => (
  <div className="challenge">
          <h3>Your new challenge is</h3>
          <h1>
            {sum.a} x {sum.b}
          </h1>
        </div>
)
export default CurrentChallenge