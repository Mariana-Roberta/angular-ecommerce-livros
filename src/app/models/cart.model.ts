export interface Cart {
  id: Number;
  userId: Number;

  getId(): number;
  setId(id: number): void;

  getUserId(): number;
  setUserId(userId: number): void;

}
