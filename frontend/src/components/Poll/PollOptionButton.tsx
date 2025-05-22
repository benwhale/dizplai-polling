import React from "react";
import { PollOption } from "../../types/poll";

export default function PollOptionButton(
  props: Readonly<{
    option: PollOption;
    isSelected: boolean;
    onSelect: (optionId: number) => void;
    isVoting: boolean;
  }>
) {
  const handleClick = (e: React.MouseEvent) => {
    e.preventDefault();
    props.onSelect(props.option.id);
  };

  return (
    <button
      onClick={handleClick}
      disabled={props.isVoting}
      className={`button ${props.isSelected ? "selected" : ""}`}
    >
      {props.option.name}
    </button>
  );
}
