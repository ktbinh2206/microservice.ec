"use client"

import { categories } from "@/lib/data"

interface CategoryFilterProps {
  selectedCategory: string
  onCategoryChange: (categoryId: string) => void
}

export default function CategoryFilter({ selectedCategory, onCategoryChange }: CategoryFilterProps) {
  return (
    <div className="mb-8">
      <h3 className="text-lg font-semibold mb-4">Lọc theo danh mục</h3>
      <div className="flex flex-wrap gap-3">
        <button
          onClick={() => onCategoryChange("")}
          className={`px-6 py-2 rounded-full font-medium transition-colors duration-200 ${
            selectedCategory === ""
              ? "bg-black text-white"
              : "bg-white text-gray-700 border border-gray-300 hover:bg-gray-50"
          }`}
        >
          Tất cả
        </button>
        {categories.map((category) => (
          <button
            key={category.id}
            onClick={() => onCategoryChange(category.id)}
            className={`px-6 py-2 rounded-full font-medium transition-colors duration-200 ${
              selectedCategory === category.id
                ? "bg-black text-white"
                : "bg-white text-gray-700 border border-gray-300 hover:bg-gray-50"
            }`}
          >
            {category.name}
          </button>
        ))}
      </div>
    </div>
  )
}
