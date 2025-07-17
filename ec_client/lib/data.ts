export interface Product {
  id: string
  name: string
  description: string
  price: number
  stock: number
  categoryId: string
  sellerId: string
  image: string
}

export interface Category {
  id: string
  name: string
  image: string
}

export const categories: Category[] = [
  { id: "1", name: "Áo thun", image: "/placeholder.svg?height=300&width=300" },
  { id: "2", name: "Quần jeans", image: "/placeholder.svg?height=300&width=300" },
  { id: "3", name: "Giày sneaker", image: "/placeholder.svg?height=300&width=300" },
  { id: "4", name: "Phụ kiện", image: "/placeholder.svg?height=300&width=300" },
]

export const products: Product[] = [
  {
    id: "1",
    name: "Áo thun Premium Cotton",
    description: "Áo thun cotton cao cấp, thoáng mát và bền đẹp",
    price: 299000,
    stock: 50,
    categoryId: "1",
    sellerId: "seller1",
    image: "/placeholder.svg?height=400&width=400",
  },
  {
    id: "2",
    name: "Quần jeans Slim Fit",
    description: "Quần jeans form slim hiện đại, phù hợp mọi dáng người",
    price: 599000,
    stock: 30,
    categoryId: "2",
    sellerId: "seller1",
    image: "/placeholder.svg?height=400&width=400",
  },
  {
    id: "3",
    name: "Giày sneaker Classic",
    description: "Giày sneaker phong cách cổ điển, thoải mái cả ngày",
    price: 899000,
    stock: 25,
    categoryId: "3",
    sellerId: "seller2",
    image: "/placeholder.svg?height=400&width=400",
  },
  {
    id: "4",
    name: "Túi xách Minimalist",
    description: "Túi xách thiết kế tối giản, phù hợp mọi phong cách",
    price: 399000,
    stock: 40,
    categoryId: "4",
    sellerId: "seller2",
    image: "/placeholder.svg?height=400&width=400",
  },
  {
    id: "5",
    name: "Áo sơ mi Oxford",
    description: "Áo sơ mi Oxford chất lượng cao, phù hợp công sở",
    price: 449000,
    stock: 35,
    categoryId: "1",
    sellerId: "seller1",
    image: "/placeholder.svg?height=400&width=400",
  },
  {
    id: "6",
    name: "Quần chinos Comfort",
    description: "Quần chinos thoải mái, phù hợp nhiều hoàn cảnh",
    price: 399000,
    stock: 45,
    categoryId: "2",
    sellerId: "seller2",
    image: "/placeholder.svg?height=400&width=400",
  },
]
