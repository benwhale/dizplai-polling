import React from "react";
import { PollOption } from "../../types/poll";

export default function PollOptionButton(
  props: Readonly<{
    option: PollOption;
    handleVote: (optionId: number) => void;
    isVoting: boolean;
  }>
) {
  return (
    <button
      onClick={() => props.handleVote(props.option.id)}
      disabled={props.isVoting}
      className="button"
    >
      {props.option.name}
    </button>
  );
}
