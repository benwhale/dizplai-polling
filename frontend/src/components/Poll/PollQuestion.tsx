import React from "react";

export default function PollQuestion(props: Readonly<{ question: string }>) {
  return <h1 className="poll-question">{props.question}</h1>;
}
