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
  const [selectedOptionId, setSelectedOptionId] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  const handleSelect = (optionId: number) => {
    setSelectedOptionId(optionId === selectedOptionId ? null : optionId);
  };

  const handleSubmit = async (e: React.MouseEvent) => {
    e.preventDefault();
    if (!selectedOptionId || hasVoted || isVoting) return;

    setIsVoting(true);
    setError(null);
    try {
      const response = await pollService.submitVote({
        optionId: selectedOptionId,
      });
      props.onPollUpdate(response);
      setHasVoted(true);
    } catch (error) {
      setError("Failed to submit vote. Please try again.");
      console.error("Error submitting vote:", error);
    } finally {
      setIsVoting(false);
    }
  };

  const totalVotes = props.options.reduce(
    (sum, option) => sum + option.voteCount,
    0
  );

  if (hasVoted) {
    return (
      <div>
        {props.options.map((option) => (
          <PollResults
            key={option.id}
            option={option}
            totalVotes={totalVotes}
          />
        ))}
      </div>
    );
  }

  return (
    <div>
      <div className="options-container">
        {props.options.map((option) => (
          <PollOptionButton
            key={option.id}
            option={option}
            isSelected={option.id === selectedOptionId}
            onSelect={handleSelect}
            isVoting={isVoting}
          />
        ))}
      </div>
      <button
        onClick={handleSubmit}
        disabled={!selectedOptionId || isVoting}
        className="button submit-button"
      >
        {isVoting ? "Submitting..." : "Submit Vote"}
      </button>
      {error && <div className="error">{error}</div>}
    </div>
  );
}
