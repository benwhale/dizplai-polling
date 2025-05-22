import React, { useState } from "react";
import { PollOption, Poll } from "../../types/poll";
import PollOptionButton from "./PollOptionButton";
import PollResults from "./PollResults";
import { pollService } from "../../services/pollService";

export default function PollOptionList(
  props: Readonly<{ options: PollOption[]; onPollUpdate: (poll: Poll) => void }>
) {
  // Get whether the user has voted from state
  const [hasVoted, setHasVoted] = useState(false);
  const [isVoting, setIsVoting] = useState(false);

  const handleVote = async (optionId: number) => {
    if (hasVoted || isVoting) return;

    setIsVoting(true);
    try {
      const response = await pollService.submitVote({ optionId: optionId });
      props.onPollUpdate(response);
      setHasVoted(true);
    } catch (error) {
      console.error(error);
    } finally {
      setIsVoting(false);
    }
  };

  const totalVotes = props.options.reduce(
    (sum, option) => sum + option.voteCount,
    0
  );

  return (
    <div>
      {props.options.map((option) => {
        if (!hasVoted) {
          return (
            <PollOptionButton
              key={option.id}
              option={option}
              handleVote={handleVote}
              isVoting={isVoting}
            />
          );
        } else {
          return (
            <PollResults
              key={option.id}
              option={option}
              totalVotes={totalVotes}
            />
          );
        }
      })}
    </div>
  );
}
