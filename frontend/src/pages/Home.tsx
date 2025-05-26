import React, { useEffect, useState } from "react";
import { Poll } from "../types/poll";
import { pollService } from "../services/pollService";
import PollQuestion from "../components/Poll/PollQuestion";
import PollOptionList from "../components/Poll/PollOptionList";

export default function Home() {
  const [poll, setPoll] = useState<Poll | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchPoll = async () => {
      try {
        setIsLoading(true);
        setError(null);
        const poll = await pollService.getActivePoll();
        setPoll(poll);
      } catch (error) {
        setError("Failed to load poll. Please try again.");
        console.error("Error fetching poll:", error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchPoll();
  }, []);

  const handlePollUpdate = (poll: Poll) => {
    setPoll(poll);
  };

  if (isLoading) return <div className="loading">Loading...</div>;
  else if (error) return <div className="error">{error}</div>;
  else if (!poll) return <div className="error">No active poll found.</div>;
  else {
    return (
      <div className="poll-container">
        <PollQuestion question={poll.question} />
        <PollOptionList
          options={poll.options}
          onPollUpdate={handlePollUpdate}
        />
      </div>
    );
  }
}
