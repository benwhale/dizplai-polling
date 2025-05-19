import React from "react";
import { PollOption } from "../../types/poll";

export default function PollResults(
  props: Readonly<{ option: PollOption; totalVotes: number }>
) {
  // Calculate percentage based on total votes
  const totalVotes = props.totalVotes;
  const percentage =
    totalVotes > 0 ? (props.option.voteCount / totalVotes) * 100 : 0;

  return (
    <div className="result">
      <div className="result-bar" style={{ width: `${percentage}%` }} />
      <div className="result-text">
        <span className="result-name">{props.option.name}</span>
        <span className="result-percentage">{percentage.toFixed(0)}%</span>
      </div>
    </div>
  );
}
