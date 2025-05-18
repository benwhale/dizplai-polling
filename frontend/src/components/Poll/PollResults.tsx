import React from "react";
import { PollOption } from "../../types/poll";

export default function PollResults(props: Readonly<{ option: PollOption }>) {
  return (
    <div>
      {props.option.name} | {props.option.voteCount} votes
    </div>
  );
}
