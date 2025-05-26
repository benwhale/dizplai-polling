import { render, screen, fireEvent } from "@testing-library/react";
import React from "react";
import PollOptionButton from "./PollOptionButton";
import { PollOption } from "../../types/poll";

const mockOption: PollOption = {
  id: 1,
  name: "Test Option",
  voteCount: 5,
};

const mockOnSelect = jest.fn();

describe("PollOptionButton", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it("renders the button with the correct text", () => {
    render(
      <PollOptionButton
        option={mockOption}
        isSelected={false}
        onSelect={mockOnSelect}
        isVoting={false}
      />
    );
    expect(screen.getByText(mockOption.name)).toBeInTheDocument();
  });

  it("calls onSelect when the button is clicked", () => {
    render(
      <PollOptionButton
        option={mockOption}
        isSelected={false}
        onSelect={mockOnSelect}
        isVoting={false}
      />
    );
    fireEvent.click(screen.getByText(mockOption.name));
    expect(mockOnSelect).toHaveBeenCalledWith(mockOption.id);
  });

  it("shows selected state when the option is selected", () => {
    render(
      <PollOptionButton
        option={mockOption}
        isSelected={true}
        onSelect={mockOnSelect}
        isVoting={false}
      />
    );
    expect(screen.getByText(mockOption.name)).toHaveClass("selected");
  });

  it("is disabled when isVoting is true", () => {
    render(
      <PollOptionButton
        option={mockOption}
        isSelected={false}
        onSelect={mockOnSelect}
        isVoting={true}
      />
    );
    expect(screen.getByText(mockOption.name)).toBeDisabled();
  });

  it("does not call onSelect when the button is disabled", () => {
    render(
      <PollOptionButton
        option={mockOption}
        isSelected={false}
        onSelect={mockOnSelect}
        isVoting={true}
      />
    );
    fireEvent.click(screen.getByText(mockOption.name));
    expect(mockOnSelect).not.toHaveBeenCalled();
  });
});
