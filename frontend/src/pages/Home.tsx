import React, { useEffect, useState } from "react";
import { Poll } from "../types/poll";
import { pollService } from "../services/pollService";
import PollQuestion from "../components/Poll/PollQuestion";
import PollOptionList from "../components/Poll/PollOptionList";

export default function Home() {
  const [poll, setPoll] = useState<Poll | null>(null);

  useEffect(() => {
    const fetchPoll = async () => {
      const poll = await pollService.getActivePoll();
      setPoll(poll);
    };
    fetchPoll();
  }, []);

  if (!poll) {
    return <div>Loading...</div>;
  } else {
    return (
      <div>
        <PollQuestion question={poll.question} />
        <PollOptionList options={poll.options} />
      </div>
    );
  }
}
