export interface Article {
  id: number;
  title: string;
  content: string;
  author_id: number;
  author_name: string;
  topic_id: number;
  topic_name: string;
  createdAt: Date;
}
