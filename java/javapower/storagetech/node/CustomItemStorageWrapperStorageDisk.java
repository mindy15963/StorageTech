package javapower.storagetech.node;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.raoulvdberge.refinedstorage.api.storage.AccessType;
import com.raoulvdberge.refinedstorage.api.storage.disk.IStorageDisk;
import com.raoulvdberge.refinedstorage.api.storage.disk.IStorageDiskContainerContext;
import com.raoulvdberge.refinedstorage.api.storage.disk.IStorageDiskListener;
import com.raoulvdberge.refinedstorage.api.util.Action;
import com.raoulvdberge.refinedstorage.tile.config.IWhitelistBlacklist;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;

public class CustomItemStorageWrapperStorageDisk implements IStorageDisk<ItemStack>
{
    private CustomStorageNetworkNode storage;
    private IStorageDisk<ItemStack> parent;

    public CustomItemStorageWrapperStorageDisk(CustomStorageNetworkNode storage, IStorageDisk<ItemStack> parent)
    {
        this.storage = storage;
        this.parent = parent;
        this.setSettings(null, storage);
    }

    @Override
    public int getPriority()
    {
        return storage.getPriority();
    }

    @Override
    public AccessType getAccessType()
    {
        return parent.getAccessType();
    }

    @Override
    public Collection<ItemStack> getStacks()
    {
        return parent.getStacks();
    }

    @Override
    @Nonnull
    public ItemStack insert(@Nonnull ItemStack stack, int size, Action action)
    {
        if (!IWhitelistBlacklist.acceptsItem(storage.getFilters(), storage.getWhitelistBlacklistMode(), storage.getCompare(), stack))
        {
            return ItemHandlerHelper.copyStackWithSize(stack, size);
        }

        return parent.insert(stack, size, action);
    }

    @Override
    @Nonnull
    public ItemStack extract(@Nonnull ItemStack stack, int size, int flags, Action action)
    {
        return parent.extract(stack, size, flags, action);
    }

    @Override
    public int getStored()
    {
        return parent.getStored();
    }

    @Override
    public int getCacheDelta(int storedPreInsertion, int size, @Nullable ItemStack remainder)
    {
        return parent.getCacheDelta(storedPreInsertion, size, remainder);
    }

    @Override
    public int getCapacity()
    {
        return parent.getCapacity();
    }

    @Override
    public void setSettings(@Nullable IStorageDiskListener listener, IStorageDiskContainerContext context)
    {
        parent.setSettings(listener, context);
    }

    @Override
    public CompoundNBT writeToNbt()
    {
        return parent.writeToNbt();
    }

    @Override
    public ResourceLocation getFactoryId()
    {
        return parent.getFactoryId();
    }
}
