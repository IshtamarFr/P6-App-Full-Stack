import { Topic } from '../features/articles/interfaces/topic.interface';

export interface User {
  id: number;
  name: string;
  email: string;
  subscriptions: Array<Topic>;
}
